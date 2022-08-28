package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorParser;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.BaseTemplateProvider;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProcessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import org.antlr.v4.runtime.*;

public class Preprocessor extends WikiTextPreprocessorBaseVisitor<String> {
  @Getter private final Set<String> behaviorSwitches = new HashSet<>();
  @Getter private final List<String> stack;
  private final PreprocessorVariables variables;
  private final TemplateProcessor templateProcessor = new TemplateProcessor();
  private final TemplateProvider templateProvider = new BaseTemplateProvider();

  public Preprocessor(PreprocessorVariables variables) {
    this.variables = variables;
    this.stack = new ArrayList<>();
  }

  public Preprocessor(PreprocessorVariables variables, List<String> stack) {
    this.variables = variables;
    this.stack = stack;
  }

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
        ctx.templateName().stream().map(RuleContext::getText).collect(Collectors.joining(""));
    Optional<String> processorVariable = variables.getVariable(templateName);
    return processorVariable.isEmpty()
        ? templateProcessor.processTemplate(templateName, templateProvider)
        : processorVariable.get();
  }

  @Override
  public String visitTemplateWithParameters(
      WikiTextPreprocessorParser.TemplateWithParametersContext ctx) {
    String templateName =
        ctx.templateName().stream().map(RuleContext::getText).collect(Collectors.joining(""));
    List<String> parameters = ctx.templateParameter().stream().map(this::visit).toList();
    return templateProcessor.processTemplate(templateName, templateProvider, parameters);
  }

  @Override
  public String visitUnnamedParameter(WikiTextPreprocessorParser.UnnamedParameterContext ctx) {
    return ctx.templateParameterKeyValue().getText();
  }

  @Override
  public String visitNamedParameter(WikiTextPreprocessorParser.NamedParameterContext ctx) {
    return String.format(
        "%s=%s",
        ctx.templateParameterKeyValue().getText(), ctx.templateParameterParameterValue().getText());
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
  public String visitParserFunction(WikiTextPreprocessorParser.ParserFunctionContext ctx) {
    String parserFunctionName = ctx.parserFunctionName().getText();
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
  public String visitAnySequence(WikiTextPreprocessorParser.AnySequenceContext ctx) {
    return ctx.getText();
  }
}
