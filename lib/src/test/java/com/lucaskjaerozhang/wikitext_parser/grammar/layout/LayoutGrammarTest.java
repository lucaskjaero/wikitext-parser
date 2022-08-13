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

    testParseTreeString(
        singleIndentation,
        "(root (baseElements (sectionContent (indentedBlock : (text (textUnion One) (textUnion  ) (textUnion level) (textUnion  ) (textUnion of) (textUnion  ) (textUnion indentation)) \\n))))");

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
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        doubleIndentation,
        "(root (baseElements (sectionContent (indentedBlock : (indentedBlock : (text (textUnion Two) (textUnion  ) (textUnion levels) (textUnion  ) (textUnion of) (textUnion  ) (textUnion indentation)) \\n)))))");

    Assertions.assertEquals(
        "<article><indentedBlock level='2'>Two levels of indentation</indentedBlock></article>",
        Parser.parseToString(doubleIndentation));
  }

  @Test
  void blockQuoteIsCorrectlyPassedThrough() {
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
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.LINE_BREAK,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.OPEN_CARAT,
            WikiTextLexer.SLASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.CLOSE_CARAT,
            WikiTextLexer.EOF));

    testParseTreeString(
        stringWithBlockQuote,
        "(root (baseElements (sectionContent (xmlTag (openTag < (text (textUnion blockquote)) >) (sectionContent (text (textUnion Some) (textUnion  ) (textUnion text))) (sectionContent \\n\\n) (sectionContent (text (textUnion More) (textUnion  ) (textUnion text))) (closeTag < / (text (textUnion blockquote)) >)))))");

    Assertions.assertEquals(blockquoteXML, Parser.parseToString(stringWithBlockQuote));
  }

  @Test
  void poemIsCorrectlyPassedThrough() {
    final String stringWithPoem =
        "<poem lang=\"fr\" style=\"float:left;\">Frère Jacques, frère Jacques,\nDormez-vous? Dormez-vous?</poem>";
    final String poemXML =
        "<article><poem  lang='fr' style='float:left;'>Frère Jacques, frère Jacques,\nDormez-vous? Dormez-vous?</poem ></article>";

    testParseTreeString(
        stringWithPoem,
        "(root (baseElements (sectionContent (xmlTag (openTag < (text (textUnion poem) (textUnion  )) (tagAttribute (text (textUnion lang)) = \" (tagAttributeValues (text (textUnion fr))) \"  ) (tagAttribute (text (textUnion style)) = \" (tagAttributeValues (text (textUnion float))) (tagAttributeValues :) (tagAttributeValues (text (textUnion left))) (tagAttributeValues ;) \") >) (sectionContent (text (textUnion Frère) (textUnion  ) (textUnion Jacques,) (textUnion  ) (textUnion frère) (textUnion  ) (textUnion Jacques,))) (sectionContent \\n) (sectionContent (text (textUnion Dormez) (textUnion -) (textUnion vous?) (textUnion  ) (textUnion Dormez) (textUnion -) (textUnion vous?))) (closeTag < / (text (textUnion poem)) >)))))");

    Assertions.assertEquals(poemXML, Parser.parseToString(stringWithPoem));
  }
}
