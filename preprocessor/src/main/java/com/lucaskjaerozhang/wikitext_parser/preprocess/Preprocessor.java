package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorParser;
import com.lucaskjaerozhang.wikitext_parser.preprocess.function.ParserFunctionEvaluator;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProcessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.RuleContext;

@Builder
public class Preprocessor extends WikiTextPreprocessorBaseVisitor<String> {
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
            .trim();
    Optional<String> processorVariable =
        Optional.ofNullable(variables.getOrDefault(templateName, null));
    return processorVariable.isEmpty()
        ? templateProcessor.processTemplate(templateName, templateProvider, calledBy, List.of())
        : processorVariable.get();
  }

  @Override
  public String visitTemplateWithParameters(
      WikiTextPreprocessorParser.TemplateWithParametersContext ctx) {
    String templateName =
        ctx.templateName().stream()
            .map(RuleContext::getText)
            .collect(Collectors.joining(""))
            .trim();
    List<String> parameters = ctx.templateParameter().stream().map(this::visit).toList();
    return templateProcessor.processTemplate(templateName, templateProvider, calledBy, parameters);
  }

  @Override
  public String visitUnnamedParameter(WikiTextPreprocessorParser.UnnamedParameterContext ctx) {
    return ctx.templateParameterKeyValue().getText().trim();
  }

  @Override
  public String visitNamedParameter(WikiTextPreprocessorParser.NamedParameterContext ctx) {
    return String.format(
        "%s=%s",
        ctx.templateParameterKeyValue().getText().trim(),
        ctx.templateParameterParameterValue().getText().trim());
  }

  /*
   * We don't output the behavior switches, but we do want to get them.
   */
  @Override
  public String visitBehaviorSwitch(WikiTextPreprocessorParser.BehaviorSwitchContext ctx) {
    String switchName = ctx.getText();
    behaviorSwitches.add(switchName);
    return "";
  }

  @Override
  public String visitParserFunctionWithBlankFirstParameter(
      WikiTextPreprocessorParser.ParserFunctionWithBlankFirstParameterContext ctx) {
    String parserFunctionName = ctx.parserFunctionName().getText().trim();
    List<String> parameters =
        Stream.concat(Stream.of(""), ctx.parserFunctionParameter().stream().map(this::visit))
            .toList();

    // Gets an Optional representing whether we implemented the function.
    // If it's not implemented then it's best to leave the function alone.
    return ParserFunctionEvaluator.evaluateFunction(parserFunctionName, parameters)
        .orElseGet(ctx::getText);
  }

  @Override
  public String visitRegularParserFunction(
      WikiTextPreprocessorParser.RegularParserFunctionContext ctx) {
    String parserFunctionName = ctx.parserFunctionName().getText().trim();
    List<String> parameters = ctx.parserFunctionParameter().stream().map(this::visit).toList();

    // Gets an Optional representing whether we implemented the function.
    // If it's not implemented then it's best to leave the function alone.
    return ParserFunctionEvaluator.evaluateFunction(parserFunctionName, parameters)
        .orElseGet(ctx::getText);
  }

  @Override
  public String visitParserFunctionTextParameter(
      WikiTextPreprocessorParser.ParserFunctionTextParameterContext ctx) {
    return ctx.parserFunctionParameterValue().getText();
  }

  @Override
  public String visitAny(WikiTextPreprocessorParser.AnyContext ctx) {
    return ctx.getText();
  }
}
