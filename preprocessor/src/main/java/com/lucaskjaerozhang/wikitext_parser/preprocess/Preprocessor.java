package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.common.metadata.WikiConstants;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorParser;
import com.lucaskjaerozhang.wikitext_parser.preprocess.function.ParserFunctionEvaluator;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProcessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

@Builder
public class Preprocessor extends WikiTextPreprocessorBaseVisitor<String> {
  private static final String NAMED_PARAMETER_PREFIX = "named_parameter_";
  private static final String POSITIONAL_PARAMETER_PREFIX = "positional_parameter_";
  private static final Pattern NAMED_PARAMETER_REGEX = Pattern.compile("([^=]+)=(.*)");

  @Builder.Default @Getter private final Set<String> behaviorSwitches = new HashSet<>();
  @Builder.Default private final List<String> calledBy = List.of();
  @Builder.Default private final Map<String, String> variables = Map.of();
  @Builder.Default private final TemplateProcessor templateProcessor = new TemplateProcessor();
  private final TemplateProvider templateProvider;

  public String preprocess(String input, boolean verbose) {
    WikiTextPreprocessorLexer lexer = new WikiTextPreprocessorLexer(CharStreams.fromString(input));
    WikiTextPreprocessorParser parser =
        new WikiTextPreprocessorParser(new CommonTokenStream(lexer));
    if (verbose) {
      parser.addErrorListener(new DiagnosticErrorListener());
      parser.setTrace(true);
    }

    return visit(parser.root());
  }

  public String preprocess(String input) {
    return preprocess(input, false);
  }

  @Override
  public String visitRoot(WikiTextPreprocessorParser.RootContext ctx) {
    return ctx.elements().stream().map(this::visit).collect(Collectors.joining(""));
  }

  /*
   * We want to skip processing things in the nowiki block, so we explicitly choose not to visit it.
   */
  @Override
  public String visitNowikiBlock(WikiTextPreprocessorParser.NowikiBlockContext ctx) {
    return ctx.getText();
  }

  /*
   * We don't want to accidentally invoke these as templates, so we explicitly ignore them.
   */
  @Override
  public String visitUnresolvedTemplateParameter(
      WikiTextPreprocessorParser.UnresolvedTemplateParameterContext ctx) {
    return ctx.getText();
  }

  @Override
  public String visitTemplateWithNoParameters(
      WikiTextPreprocessorParser.TemplateWithNoParametersContext ctx) {
    String templateName =
        ctx.templateName().stream()
            .map(RuleContext::getText)
            .collect(Collectors.joining(""))
            .strip();
    Optional<String> processorVariable =
        Optional.ofNullable(variables.getOrDefault(templateName, null));
    return processorVariable.isEmpty()
        ? templateProcessor.processTemplate(
            templateName, templateProvider, calledBy, List.of(), Map.of())
        : processorVariable.get();
  }

  @Override
  public String visitTemplateWithParameters(
      WikiTextPreprocessorParser.TemplateWithParametersContext ctx) {
    String templateName =
        ctx.templateName().stream()
            .map(RuleContext::getText)
            .collect(Collectors.joining(""))
            .strip();
    List<String> params = ctx.templateParameter().stream().map(this::visit).toList();
    Map<Boolean, List<String>> parameters =
        params.stream()
            .map(p -> Optional.of(p).orElse(""))
            .collect(Collectors.partitioningBy(p -> p.startsWith(NAMED_PARAMETER_PREFIX)));
    Map<String, String> namedParameters =
        parameters.get(true).stream()
            .map(p -> p.replaceFirst(NAMED_PARAMETER_PREFIX, ""))
            .map(param -> NAMED_PARAMETER_REGEX.matcher(param).results().toList().get(0))
            .collect(Collectors.toMap(r -> r.group(1), r -> r.group(2)));
    List<String> positionalParameters =
        parameters.get(false).stream()
            .map(p -> p.replaceFirst(POSITIONAL_PARAMETER_PREFIX, ""))
            .toList();

    return templateProcessor.processTemplate(
        templateName, templateProvider, calledBy, positionalParameters, namedParameters);
  }

  @Override
  public String visitUnnamedParameter(WikiTextPreprocessorParser.UnnamedParameterContext ctx) {
    String positionalParameter =
        ctx.templateParameterKeyValues().stream()
            .map(RuleContext::getText)
            .collect(Collectors.joining())
            .strip();
    return String.format("%s%s", POSITIONAL_PARAMETER_PREFIX, positionalParameter);
  }

  @Override
  public String visitNamedParameter(WikiTextPreprocessorParser.NamedParameterContext ctx) {
    return String.format(
        "%s%s=%s",
        NAMED_PARAMETER_PREFIX,
        ctx.templateParameterKeyValues().stream()
            .map(RuleContext::getText)
            .collect(Collectors.joining())
            .strip(),
        ctx.templateParameterParameterValues().stream()
            .map(RuleContext::getText)
            .collect(Collectors.joining())
            .strip());
  }

  @Override
  public String visitBehaviorSwitch(WikiTextPreprocessorParser.BehaviorSwitchContext ctx) {
    String switchName = ctx.getText();
    if (WikiConstants.isBehaviorSwitch(switchName)) {
      // We don't output the behavior switches, but we do want to get them.
      behaviorSwitches.add(switchName);
      return "";
    }

    // Not a behavior switch? Leave it alone.
    return switchName;
  }

  @Override
  public String visitParserFunctionWithBlankFirstParameter(
      WikiTextPreprocessorParser.ParserFunctionWithBlankFirstParameterContext ctx) {
    String parserFunctionName = ctx.parserFunctionName().getText().strip();
    List<Callable<String>> parameters =
        Stream.concat(
                Stream.of(() -> ""),
                ctx.parserFunctionParameter().stream()
                    .map(p -> (Callable<String>) () -> visit(p).strip()))
            .toList();

    // Gets an Optional representing whether we implemented the function.
    // If it's not implemented then it's best to leave the function alone.
    return ParserFunctionEvaluator.evaluateFunction(parserFunctionName, parameters)
        .orElseGet(ctx::getText);
  }

  @Override
  public String visitRegularParserFunction(
      WikiTextPreprocessorParser.RegularParserFunctionContext ctx) {
    String parserFunctionName = ctx.parserFunctionName().getText().strip();

    List<Callable<String>> parameters =
        ctx.parserFunctionParameter().stream()
            .map(p -> (Callable<String>) () -> visit(p).strip())
            .toList();

    // Gets an Optional representing whether we implemented the function.
    // If it's not implemented then it's best to leave the function alone.
    return ParserFunctionEvaluator.evaluateFunction(parserFunctionName, parameters)
        .orElseGet(ctx::getText);
  }

  @Override
  public String visitParserFunctionParameter(
      WikiTextPreprocessorParser.ParserFunctionParameterContext ctx) {
    return ctx.parserFunctionParameterValues().stream()
        .map(this::visit)
        .collect(Collectors.joining());
  }

  @Override
  public String visitLink(WikiTextPreprocessorParser.LinkContext ctx) {
    String namespace =
        ctx.linkNamespaceComponent().stream()
            .map(RuleContext::getText)
            .collect(Collectors.joining());
    String linkTarget =
        ctx.linkTarget().stream().map(RuleContext::getText).collect(Collectors.joining());
    return ctx.elements().isEmpty()
        ? String.format("[[%s%s]]", namespace, linkTarget)
        : String.format(
            "[[%s%s|%s]]",
            namespace,
            linkTarget,
            ctx.elements().stream().map(this::visit).collect(Collectors.joining()));
  }

  @Override
  public String visitTerminal(TerminalNode node) {
    return node.getText();
  }

  @Override
  public String visitAny(WikiTextPreprocessorParser.AnyContext ctx) {
    return ctx.getText();
  }
}
