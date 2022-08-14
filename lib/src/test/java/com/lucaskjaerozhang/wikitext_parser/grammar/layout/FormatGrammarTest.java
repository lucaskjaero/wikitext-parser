package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

import com.lucaskjaerozhang.wikitext_parser.Parser;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikitextGrammarBaseTest;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class FormatGrammarTest extends WikitextGrammarBaseTest {
  @Test
  void italicTextIsRecognized() {
    final String italicText = "''italic''";
    testLexerTokenTypes(
        italicText,
        Arrays.asList(
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.EOF));

    Assertions.assertEquals(
        "<article><italic>italic</italic></article>", Parser.parseToString(italicText));
  }

  @Test
  void boldTextIsRecognized() {
    final String boldText = "'''bold'''";
    testLexerTokenTypes(
        boldText,
        Arrays.asList(
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.EOF));

    Assertions.assertEquals("<article><bold>bold</bold></article>", Parser.parseToString(boldText));
  }

  @Test
  void boldAndItalicTextIsRecognized() {
    final String boldText = "'''''bolditalic'''''";
    testLexerTokenTypes(
        boldText,
        Arrays.asList(
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.SINGLE_QUOTE,
            WikiTextLexer.EOF));

    Assertions.assertEquals(
        "<article><bold><italic>bolditalic</italic></bold></article>",
        Parser.parseToString(boldText));
  }

  @Test
  void codeBlocksDoNotBreakParser() {
    final String stringWithCode = "function <code>int m2()</code> is nice.\n";
    final String codeXML = "<article>function <code>int m2()</code> is nice.\n</article>";

    Assertions.assertEquals(codeXML, Parser.parseToString(stringWithCode));
  }

  @Test
  void syntaxHighlightingBlocksDoNotBreakParser() {
    final String stringWithCode =
            """
                    <syntaxhighlight lang="cpp">
                    #include <iostream>
                    int m2 (int ax, char *p_ax) {
                      std::cout <<"Hello World!";
                      return 0;
                    }</syntaxhighlight>""";
    final String codeXML =
            """
                    <article><syntaxhighlight lang='cpp'>
                    #include <iostream>
                    int m2 (int ax, char *p_ax) {
                    std::cout <<"Hello World!";
                    return 0;
                    }</syntaxhighlight></article>""";

    Assertions.assertEquals(codeXML, Parser.parseToString(stringWithCode));
  }
}
