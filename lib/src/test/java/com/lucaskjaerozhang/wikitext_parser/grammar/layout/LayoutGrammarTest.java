package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

import com.lucaskjaerozhang.wikitext_parser.Parser;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikitextGrammarBaseTest;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class LayoutGrammarTest extends WikitextGrammarBaseTest {
  @Test
  void singlyIndentedBlocksAreCorrectlyParsed() {
    final String singleIndentation = ":One level of indentation\n";
    testLexerTokenTypes(
        singleIndentation,
        Arrays.asList(
            WikiTextLexer.COLON, WikiTextLexer.TEXT, WikiTextLexer.NEWLINE, WikiTextLexer.EOF));

    testParseTreeString(
        singleIndentation,
        "(root (baseElements (sectionContent (indentedBlock : One level of indentation \\n))))");

    Assertions.assertEquals(
        "<article><indentedBlock level='1'>One level of indentation</indentedBlock></article>",
        Parser.parseToString(singleIndentation));
  }

  @Test
  void doublyIndentedBlocksAreCorrectlyParsed() {
    final String doubleIndentation = "::Two levels of indentation\n";
    testLexerTokenTypes(
        doubleIndentation,
        Arrays.asList(
            WikiTextLexer.COLON,
            WikiTextLexer.COLON,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        doubleIndentation,
        "(root (baseElements (sectionContent (indentedBlock : (indentedBlock : Two levels of indentation \\n)))))");

    Assertions.assertEquals(
        "<article><indentedBlock level='2'>Two levels of indentation</indentedBlock></article>",
        Parser.parseToString(doubleIndentation));
  }

  @Test
  void blockQuoteIsRecognized() {
    final String stringWithBlockQuote = "<blockquote>Some text\n\nMore text</blockquote>";
    final String blockquoteXML =
        """
            <article><blockquote>Some text

            More text</blockquote></article>""";

    testLexerTokenTypes(
        stringWithBlockQuote,
        Arrays.asList(
            WikiTextLexer.OPEN_CARAT,
            WikiTextLexer.TEXT,
            WikiTextLexer.CLOSE_CARAT,
            WikiTextLexer.TEXT,
            WikiTextLexer.LINE_BREAK,
            WikiTextLexer.TEXT,
            WikiTextLexer.OPEN_CARAT,
            WikiTextLexer.SLASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.CLOSE_CARAT,
            WikiTextLexer.EOF));

    testParseTreeString(
        stringWithBlockQuote,
        "(root (baseElements (sectionContent (xmlTag (openTag < blockquote >) (sectionContent Some text) (sectionContent \\n\\n) (sectionContent More text) (closeTag < / blockquote >)))))");

    Assertions.assertEquals(blockquoteXML, Parser.parseToString(stringWithBlockQuote));
  }
}
