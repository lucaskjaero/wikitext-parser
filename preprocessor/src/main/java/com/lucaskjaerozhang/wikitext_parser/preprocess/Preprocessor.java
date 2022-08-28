package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorParser;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.antlr.v4.runtime.*;

public class Preprocessor extends WikiTextPreprocessorBaseVisitor<String> {
  @Getter private final Set<String> behaviorSwitches = new HashSet<>();
  private final PreprocessorVariables variables;

  public Preprocessor(PreprocessorVariables variables) {
    this.variables = variables;
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
  public String visitVariable(WikiTextPreprocessorParser.VariableContext ctx) {
    String variableName = ctx.parserFunctionName().getText();
    return variables.getVariable(variableName);
  }

  @Override
  public String visitParserFunctionWithParameters(
      WikiTextPreprocessorParser.ParserFunctionWithParametersContext ctx) {
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
