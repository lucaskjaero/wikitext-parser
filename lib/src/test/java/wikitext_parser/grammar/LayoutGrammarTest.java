package wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import java.util.Arrays;
import java.util.List;
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
  void plainTextIsRecognized() {
    final String plainTextString = "This is just plain text";
    final List<Integer> tokenTypes = Arrays.asList(WikiTextLexer.TEXT, WikiTextLexer.EOF);

    testLexerTokenTypes(plainTextString, tokenTypes);

    Assertions.assertEquals(1, getResultsFromXPATH(plainTextString, "//TEXT").size());

    testParseTreeString(plainTextString, "([] ([12] ([23 12] This is just plain text)))");
  }

  @Test
  void headersAreRecognized() {
    final String stringWithHeaders = "== Header ==";
    final String stringWithoutHeaders = "Some other thing";

    testLexerTokenTypes(
        stringWithHeaders,
        Arrays.asList(
            WikiTextLexer.T__1, WikiTextLexer.TEXT, WikiTextLexer.T__1, WikiTextLexer.EOF));

    Assertions.assertEquals(1, getResultsFromXPATH(stringWithHeaders, "//TEXT").size());
    Assertions.assertEquals(1, getResultsFromXPATH(stringWithHeaders, "//header").size());
    Assertions.assertEquals(0, getResultsFromXPATH(stringWithoutHeaders, "//header").size());

    testParseTreeString(stringWithHeaders, "([] ([12] ([17 12] == ([37 17 12]  Header ) ==)))");
  }

  @Test
  void horizontalRulesAreRecognized() {
    final String stringWithHorizontalRule = "Some text\n----\nMore text";
    testLexerTokenTypes(
        stringWithHorizontalRule,
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HORIZONTAL_RULE,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF));

    Assertions.assertEquals(2, getResultsFromXPATH(stringWithHorizontalRule, "//TEXT").size());
    Assertions.assertEquals(0, getResultsFromXPATH(stringWithHorizontalRule, "//header").size());
    Assertions.assertEquals(
        1, getResultsFromXPATH(stringWithHorizontalRule, "//HORIZONTAL_RULE").size());

    testParseTreeString(
        stringWithHorizontalRule,
        "([] ([12] ([23 12] Some text)) ([12] \\n) ([12] ----) ([12] \\n) ([12] ([23 12] More text)))");
  }

  @Test
  void lineBreaksAreRecognized() {
    final String stringWithIntentionalBreak = "Some text\n\nMore text";
    final String stringWithoutIntentionalBreak = "Some text\nMore text";
    testLexerTokenTypes(
        stringWithIntentionalBreak,
        Arrays.asList(
            WikiTextLexer.TEXT, WikiTextLexer.LINE_BREAK, WikiTextLexer.TEXT, WikiTextLexer.EOF));

    Assertions.assertEquals(
        1, getResultsFromXPATH(stringWithIntentionalBreak, "//LINE_BREAK").size());
    Assertions.assertEquals(
        0, getResultsFromXPATH(stringWithoutIntentionalBreak, "//LINE_BREAK").size());

    testParseTreeString(
        stringWithIntentionalBreak,
        "([] ([12] ([23 12] Some text)) ([12] \\n\\n) ([12] ([23 12] More text)))");
  }

  @Test
  void indentedBlocksAreRecognized() {
    final String singleIndentation = ":One level of indentation\n";
    final String doubleIndentation = "::Two levels of indentation\n";
    testLexerTokenTypes(
        singleIndentation,
        Arrays.asList(
            WikiTextLexer.T__6, WikiTextLexer.TEXT, WikiTextLexer.NEWLINE, WikiTextLexer.EOF));

    testParseTreeString(
        singleIndentation, "([] ([12] ([18 12] : ([61 18 12] One level of indentation) \\n)))");

    testParseTreeString(
        doubleIndentation,
        "([] ([12] ([18 12] : ([59 18 12] : ([61 59 18 12] Two levels of indentation) \\n))))");
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
        "([] ([12] ([19 12] <blockquote> ([67 19 12] ([23 67 19 12] Some text)) ([67 19 12] \\n\\n) ([67 19 12] ([23 67 19 12] More text)) </blockquote>)))");
  }
}
