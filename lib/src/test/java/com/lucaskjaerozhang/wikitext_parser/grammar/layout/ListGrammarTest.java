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
class ListGrammarTest extends WikitextGrammarBaseTest {
  @Test
  void unorderedListsAreCorrectlyParsed() {
    final String unorderedList =
        """
                    * Item one
                    * Item two
                    ** Item two a
                    ** Item two b
                    * Item three\n""";
    testLexerTokenTypes(
        unorderedList,
        Arrays.asList(
            WikiTextLexer.ASTERISK,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        unorderedList,
        "(root (baseElements (sectionContent (unorderedList (unorderedListItem *  Item one \\n) (unorderedListItem *  Item two \\n) (unorderedListItem * (unorderedListItem *  Item two a \\n)) (unorderedListItem * (unorderedListItem *  Item two b \\n)) (unorderedListItem *  Item three \\n)))))");

    Assertions.assertEquals(
        "<article><list type='unordered'><listItem> Item one</listItem><listItem> Item two</listItem><listItem></listItem><listItem></listItem><listItem> Item three</listItem></list></article>",
        Parser.parseToString(unorderedList));
  }

  @Test
  void orderedListsAreCorrectlyParsed() {
    final String orderedList =
        """
                    # Item one
                    # Item two
                    ## Item two a
                    ## Item two b
                    # Item three\n""";
    testLexerTokenTypes(
        orderedList,
        Arrays.asList(
            WikiTextLexer.HASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.HASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.HASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        orderedList,
        "(root (baseElements (sectionContent (orderedList (orderedListItem #  Item one \\n) (orderedListItem #  Item two \\n) (orderedListItem # (orderedListItem #  Item two a \\n)) (orderedListItem # (orderedListItem #  Item two b \\n)) (orderedListItem #  Item three \\n)))))");

    Assertions.assertEquals(
        "<article><list type='ordered'><listItem> Item one</listItem><listItem> Item two</listItem><listItem></listItem><listItem></listItem><listItem> Item three</listItem></list></article>",
        Parser.parseToString(orderedList));
  }
}
