package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.grammar.parse.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.parse.WikiTextParser;
import java.util.List;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/** Here we invoke antlr to build the parse tree. */
public class ParseTreeBuilder {
  /**
   * Creates a lexer from the given text. You probably want a parser.
   *
   * @param text The text to run the lexer on.
   * @param listeners Error listeners to notify if there is a problem during lexing.
   * @return The lexer after it has completed running.
   */
  public static WikiTextLexer getLexerFromText(String text, List<ANTLRErrorListener> listeners) {
    WikiTextLexer lexer = new WikiTextLexer(CharStreams.fromString(text));
    listeners.forEach(lexer::addErrorListener);
    return lexer;
  }

  /**
   * Builds the parse tree
   *
   * @param text The text to parse
   * @param listeners Error listeners to notify if there is a problem during parsing.
   * @param trace Whether to give trace logs when parsing. You only want this during testing.
   * @return The parse tree.
   */
  public static WikiTextParser getParserFromText(
      String text, List<ANTLRErrorListener> listeners, boolean trace) {
    WikiTextParser parser =
        new WikiTextParser(new CommonTokenStream(getLexerFromText(text, listeners)));
    parser.setTrace(trace);
    listeners.forEach(parser::addErrorListener);
    return parser;
  }

  /**
   * Does the parsing with the included error listeners and the option to enable trace logs.
   *
   * @param text The text to parse
   * @param listeners Error listeners to notify if there is a problem during parsing.
   * @param trace Whether to give trace logs when parsing. You only want this during testing.
   * @return The AST built from the input.
   */
  public static WikiTextElement visitTreeFromText(
      String text, List<ANTLRErrorListener> listeners, boolean trace) {
    return new WikitextParseTreeVisitor().visit(getParserFromText(text, listeners, trace).root());
  }

  /**
   * Does the parsing with the option to enable trace logs.
   *
   * @param text The text to parse
   * @param trace Whether to give trace logs when parsing. You only want this during testing.
   * @return The AST built from the input.
   */
  public static WikiTextElement visitTreeFromText(String text, boolean trace) {
    return visitTreeFromText(text, List.of(), trace);
  }

  /**
   * Does the parsing with default settings.
   *
   * @param text The text to parse
   * @return The AST built from the input.
   */
  public static WikiTextElement visitTreeFromText(String text) {
    return visitTreeFromText(text, false);
  }
}
