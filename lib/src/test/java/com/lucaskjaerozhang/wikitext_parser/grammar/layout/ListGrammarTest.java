package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

import com.lucaskjaerozhang.wikitext_parser.Parser;
import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class ListGrammarTest extends WikitextBaseTest {
  @Test
  void unorderedListsAreCorrectlyParsed() {
    final String unorderedList =
        """
                    * One
                    * Two
                    ** TwoA
                    ** TwoB
                    * Three\n""";
    testLexerTokenTypes(
        unorderedList,
        Arrays.asList(
            WikiTextLexer.ASTERISK,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.ASTERISK,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        unorderedList,
        "(root (baseElements (sectionContent (unorderedList (unorderedListItem * (text (textUnion  ) (textUnion One)) \\n) (unorderedListItem * (text (textUnion  ) (textUnion Two)) \\n) (unorderedListItem * (unorderedListItem * (text (textUnion  ) (textUnion TwoA)) \\n)) (unorderedListItem * (unorderedListItem * (text (textUnion  ) (textUnion TwoB)) \\n)) (unorderedListItem * (text (textUnion  ) (textUnion Three)) \\n)))))");

    Assertions.assertEquals(
        "<article><list type='unordered'><listItem level='1'> One</listItem><listItem level='1'> Two</listItem><listItem level='2'> TwoA</listItem><listItem level='2'> TwoB</listItem><listItem level='1'> Three</listItem></list></article>",
        Parser.parseToString(unorderedList));
  }

  @Test
  void orderedListsAreCorrectlyParsed() {
    final String orderedList =
        """
                    # One
                    # Two
                    ## TwoA
                    ## TwoB
                    # Three\n""";
    testLexerTokenTypes(
        orderedList,
        Arrays.asList(
            WikiTextLexer.HASH,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.HASH,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.HASH,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HASH,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        orderedList,
        "(root (baseElements (sectionContent (orderedList (orderedListItem # (text (textUnion  ) (textUnion One)) \\n) (orderedListItem # (text (textUnion  ) (textUnion Two)) \\n) (orderedListItem # (orderedListItem # (text (textUnion  ) (textUnion TwoA)) \\n)) (orderedListItem # (orderedListItem # (text (textUnion  ) (textUnion TwoB)) \\n)) (orderedListItem # (text (textUnion  ) (textUnion Three)) \\n)))))");

    Assertions.assertEquals(
        "<article><list type='ordered'><listItem level='1'> One</listItem><listItem level='1'> Two</listItem><listItem level='2'> TwoA</listItem><listItem level='2'> TwoB</listItem><listItem level='1'> Three</listItem></list></article>",
        Parser.parseToString(orderedList));
  }

  @Test
  void singleLineDescriptionListsAreCorrectlyParsed() {
    final String singleLineDescriptionList = "; Title : Item\n";
    testLexerTokenTypes(
        singleLineDescriptionList,
        Arrays.asList(
            WikiTextLexer.SEMICOLON,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.COLON,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        singleLineDescriptionList,
        "(root (baseElements (sectionContent (descriptionList ; (text (textUnion  ) (textUnion Title) (textUnion  )) (descriptionListItem : (text (textUnion  ) (textUnion Item)) \\n)))))");

    Assertions.assertEquals(
        "<article><list title='Title' type='description'><listItem> Item</listItem></list></article>",
        Parser.parseToString(singleLineDescriptionList));
  }

  @Test
  void multilineDescriptionListsAreCorrectlyParsed() {
    final String multilineDescriptionList =
        """
                    ; Title
                    : One
                    : Two\n""";
    testLexerTokenTypes(
        multilineDescriptionList,
        Arrays.asList(
            WikiTextLexer.SEMICOLON,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.COLON,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.COLON,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.EOF));

    testParseTreeString(
        multilineDescriptionList,
        "(root (baseElements (sectionContent (descriptionList ; (text (textUnion  ) (textUnion Title)) \\n (descriptionListItem : (text (textUnion  ) (textUnion One)) \\n) (descriptionListItem : (text (textUnion  ) (textUnion Two)) \\n)))))");

    Assertions.assertEquals(
        "<article><list title='Title' type='description'><listItem> One</listItem><listItem> Two</listItem></list></article>",
        Parser.parseToString(multilineDescriptionList));
  }
}
