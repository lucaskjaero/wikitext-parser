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
    final String unorderedListXML =
        "<article><list type='unordered'><listItem level='1'> One</listItem><listItem level='1'> Two</listItem><listItem level='2'> TwoA</listItem><listItem level='2'> TwoB</listItem><listItem level='1'> Three</listItem></list></article>";

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

    testTranslation(unorderedList, unorderedListXML);
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

    testTranslation(
        orderedList,
        "<article><list type='ordered'><listItem level='1'> One</listItem><listItem level='1'> Two</listItem><listItem level='2'> TwoA</listItem><listItem level='2'> TwoB</listItem><listItem level='1'> Three</listItem></list></article>");
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

    testTranslation(
        singleLineDescriptionList,
        "<article><list title='Title' type='description'><listItem> Item</listItem></list></article>");
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

    testTranslation(
        multilineDescriptionList,
        "<article><list title='Title' type='description'><listItem> One</listItem><listItem> Two</listItem></list></article>");
  }
}
