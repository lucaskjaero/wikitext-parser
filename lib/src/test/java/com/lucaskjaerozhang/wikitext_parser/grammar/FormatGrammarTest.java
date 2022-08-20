package com.lucaskjaerozhang.wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class FormatGrammarTest extends WikitextBaseTest {
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

    testTranslation(italicText, "<article><italic>italic</italic></article>");
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

    testTranslation(boldText, "<article><bold>bold</bold></article>");
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

    testTranslation(boldText, "<article><bold><italic>bolditalic</italic></bold></article>");
  }

  @Test
  void codeBlocksDoNotBreakParser() {
    final String stringWithCode = "function <code>int m2()</code> is nice.\n";
    final String codeXML = "<article>function <code>int m2()</code> is nice.\n</article>";

    testTranslation(stringWithCode, codeXML);
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
                    <article><syntaxhighlight lang="cpp">
                    #include <iostream>
                    int m2 (int ax, char *p_ax) {
                    std::cout <<"Hello World!";
                    return 0;
                    }</syntaxhighlight></article>""";

    testTranslation(stringWithCode, codeXML);
  }

  @Test
  void characterReferencesDoNotBreakParser() {
    final String stringWithCharacterReference = "&Agrave; &#xC0; &Aring; &AElig; &Oslash;";
    final String characterReferenceXML =
        "<article>&Agrave; &#xC0; &Aring; &AElig; &Oslash;</article>";

    testTranslation(stringWithCharacterReference, characterReferenceXML);
  }

  @Test
  void mathBlocksDoNotBreakParser() {
    final String stringWithCharacterReference =
        "<math>2x \\times 4y \\div 6z + 8 - \\frac {y}{z^2} = 0</math>";
    final String characterReferenceXML =
        "<article><math>2x \\times 4y \\div 6z + 8 - \\frac {y}{z^2} = 0</math></article>";

    testTranslation(stringWithCharacterReference, characterReferenceXML);
  }
}
