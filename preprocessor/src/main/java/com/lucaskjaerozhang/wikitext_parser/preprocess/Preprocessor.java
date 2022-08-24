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
  @Getter private Set<String> behaviorSwitches = new HashSet<>();
  private final PreprocessorVariables variables;

  public Preprocessor(PreprocessorVariables variables) {
    this.variables = variables;
  }

  public String preprocess(String input) {
    WikiTextPreprocessorLexer lexer = new WikiTextPreprocessorLexer(CharStreams.fromString(input));
    //    CommonTokenStream tokensStream = new CommonTokenStream(lexer);
    //    tokensStream.fill();
    //    List<Token> tokens = tokensStream.getTokens();

    WikiTextPreprocessorParser parser =
        new WikiTextPreprocessorParser(new CommonTokenStream(lexer));
    parser.setTrace(true);
    parser.addErrorListener(new DiagnosticErrorListener());
    return visit(parser.root());
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
    return ParserFunctionEvaluator.evaluateFunction(parserFunctionName, parameters);
  }

  @Override
  public String visitParserFunctionTextParameter(
      WikiTextPreprocessorParser.ParserFunctionTextParameterContext ctx) {
    return ctx.TEXT().getText();
  }

  @Override
  public String visitParserFunctionFunctionParameter(
      WikiTextPreprocessorParser.ParserFunctionFunctionParameterContext ctx) {
    return visit(ctx.parserFunction());
  }

  @Override
  public String visitAnySequence(WikiTextPreprocessorParser.AnySequenceContext ctx) {
    return ctx.getText();
  }
}
