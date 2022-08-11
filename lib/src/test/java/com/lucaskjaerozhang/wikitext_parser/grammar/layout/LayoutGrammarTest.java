package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikitextGrammarBaseTest;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class LayoutGrammarTest extends WikitextGrammarBaseTest {
  @Test
  void indentedBlocksAreRecognized() {
    final String singleIndentation = ":One level of indentation\n";
    final String doubleIndentation = "::Two levels of indentation\n";
    testLexerTokenTypes(
        singleIndentation,
        Arrays.asList(
            WikiTextLexer.COLON, WikiTextLexer.TEXT, WikiTextLexer.NEWLINE, WikiTextLexer.EOF));

    testParseTreeString(
        singleIndentation,
        "(root (sectionContent (indentedBlock : One level of indentation \\n)))");

    testParseTreeString(
        doubleIndentation,
        "(root (sectionContent (indentedBlock : (indentedBlock : Two levels of indentation \\n))))");
  }

  @Test
  void blockQuoteIsRecognized() {
    final String stringWithBlockQuote = "<blockquote>Some text\n\nMore text</blockquote>";
    testLexerTokenTypes(
        stringWithBlockQuote,
        Arrays.asList(
            WikiTextLexer.BLOCKQUOTE_OPEN,
            WikiTextLexer.TEXT,
            WikiTextLexer.LINE_BREAK,
            WikiTextLexer.TEXT,
            WikiTextLexer.BLOCKQUOTE_CLOSE,
            WikiTextLexer.EOF));

    testParseTreeString(
        stringWithBlockQuote,
        "(root (sectionContent (blockQuote <blockquote> (sectionContent Some text) (sectionContent \\n\\n) (sectionContent More text) </blockquote>)))");
  }
}
