package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class LayoutGrammarTest extends WikitextBaseTest {
  @Test
  void singlyIndentedBlocksAreCorrectlyParsed() {
    final String singleIndentation = ":One level of indentation\n";
    testLexerTokenTypes(
        singleIndentation,
        Arrays.asList(
            WikiTextLexer.COLON,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testTranslation(
        singleIndentation,
        "<article><indentedBlock level='1'>One level of indentation</indentedBlock></article>");
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
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testTranslation(
        doubleIndentation,
        "<article><indentedBlock level='2'>Two levels of indentation</indentedBlock></article>");
  }

  @Test
  void blockQuoteIsCorrectlyPassedThrough() {
    final String stringWithBlockQuote = "<blockquote>Some text\n\nMore text</blockquote>";
    final String blockquoteXML =
        """
            <article><blockquote>Some text<br />More text</blockquote></article>""";

    testLexerTokenTypes(
        stringWithBlockQuote,
        Arrays.asList(
            WikiTextLexer.OPEN_CARAT,
            WikiTextLexer.TEXT,
            WikiTextLexer.CLOSE_CARAT,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.OPEN_CARAT,
            WikiTextLexer.SLASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.CLOSE_CARAT,
            WikiTextLexer.EOF));

    testTranslation(stringWithBlockQuote, blockquoteXML);
  }

  @Test
  void poemIsCorrectlyPassedThrough() {
    final String stringWithPoem =
        "<poem lang=\"fr\" style=\"float:left;\">Frère Jacques, frère Jacques,\nDormez-vous? Dormez-vous?</poem>";
    final String poemXML =
        """
                    <article><poem lang="fr" style="float:left;">Frère Jacques, frère Jacques,
                    Dormez-vous? Dormez-vous?</poem></article>""";

    testTranslation(stringWithPoem, poemXML);
  }

  @Test
  void xmlPreservesQuoteLevelsPassedThrough() {
    final String containerTagWithQuotes = "<a b=\"B\" c='c'>d</blockquote>";
    final String standaloneTagWithQuotes = "<a b=\"B\" c='c'/>";

    final String containerTagXML = "<article><a b=\"B\" c='c'>d</a></article>";
    final String standaloneTagXML = "<article><a b=\"B\" c='c' /></article>";

    testTranslation(containerTagWithQuotes, containerTagXML);
    testTranslation(standaloneTagWithQuotes, standaloneTagXML);
  }
}
