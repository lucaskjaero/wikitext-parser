package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import java.util.List;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/** Here we invoke antlr to build the parse tree. */
public class SetupParse {
  public static WikiTextLexer getLexerFromText(String text, List<ANTLRErrorListener> listeners) {
    WikiTextLexer lexer = new WikiTextLexer(CharStreams.fromString(text));
    listeners.forEach(lexer::addErrorListener);
    return lexer;
  }

  /**
   * Builds the parse tree
   *
   * @param text The text to parse
   * @param listeners A set of error listeners to react to parse problems
   * @return The parse tree.
   */
  public static WikiTextParser getParserFromText(String text, List<ANTLRErrorListener> listeners) {
    WikiTextParser parser =
        new WikiTextParser(new CommonTokenStream(getLexerFromText(text, listeners)));
    listeners.forEach(parser::addErrorListener);
    return parser;
  }

  public static WikiTextElement visitTreeFromText(String text, List<ANTLRErrorListener> listeners) {
    return new WikitextVisitor().visit(getParserFromText(text, listeners).root());
  }

  public static WikiTextElement visitTreeFromText(String text) {
    return visitTreeFromText(text, List.of(new WikiTextErrorListener()));
  }

  private SetupParse() {
    throw new IllegalStateException("Utility class");
  }
}
