package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class SetupParse {
  public static WikiTextLexer getLexerFromText(String text, ANTLRErrorListener listener) {
    WikiTextLexer lexer = new WikiTextLexer(CharStreams.fromString(text));
    lexer.addErrorListener(listener);
    return lexer;
  }

  public static WikiTextParser getParserFromText(String text, ANTLRErrorListener listener) {
    WikiTextParser parser =
        new WikiTextParser(new CommonTokenStream(getLexerFromText(text, listener)));
    parser.addErrorListener(listener);
    return parser;
  }

  public static WikiTextNode visitTreeFromText(String text, ANTLRErrorListener listener) {
    return new WikitextVisitor().visit(getParserFromText(text, listener).root());
  }

  public static WikiTextNode visitTreeFromText(String text) {
    return visitTreeFromText(text, new WikiTextErrorListener());
  }

  private SetupParse() {
    throw new IllegalStateException("Utility class");
  }
}
