package com.lucaskjaerozhang.wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.grammar.parse.WikiTextLexer;
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
  void uppercaseCodeBlocksDoNotBreakParser() {
    final String stringWithCode = "function <CODE>int m2()</CODE> is nice.\n";
    final String codeXML = "<article>function <CODE>int m2()</CODE> is nice.\n</article>";

    testTranslation(stringWithCode, codeXML);
  }

  @Test
  void syntaxHighlightingBlocksDoNotBreakParser() {
    final String lowercaseTag = "syntaxhighlight";
    final String uppercaseTag = "SYNTAXHIGHLIGHT";

    final String stringWithCode =
        """
        <%s lang="cpp">
        #include <iostream>
        int m2 (int ax, char *p_ax) {
          std::cout <<"Hello World!";
          return 0;
        }</%s>""";
    final String codeXML =
        """
        <article><%s lang="cpp">
        #include <iostream>
        int m2 (int ax, char *p_ax) {
        std::cout <<"Hello World!";
        return 0;
        }</%s></article>""";

    testTranslation(
        String.format(stringWithCode, lowercaseTag, lowercaseTag),
        String.format(codeXML, lowercaseTag, lowercaseTag));

    testTranslation(
        String.format(stringWithCode, uppercaseTag, uppercaseTag),
        String.format(codeXML, uppercaseTag, uppercaseTag));
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
    final String stringWithMathBlock =
        "<math>2x \\times 4y \\div 6z + 8 - \\frac {y}{z^2} = 0</math>";
    final String mathBlockXML =
        "<article><math>2x \\times 4y \\div 6z + 8 - \\frac {y}{z^2} = 0</math></article>";

    testTranslation(stringWithMathBlock, mathBlockXML);
  }

  @Test
  void uppercaseMathBlocksDoNotBreakParser() {
    final String stringWithMathBlock =
        "<MATH>2x \\times 4y \\div 6z + 8 - \\frac {y}{z^2} = 0</MATH>";
    final String mathBlockXML =
        "<article><MATH>2x \\times 4y \\div 6z + 8 - \\frac {y}{z^2} = 0</MATH></article>";

    testTranslation(stringWithMathBlock, mathBlockXML);
  }
}
