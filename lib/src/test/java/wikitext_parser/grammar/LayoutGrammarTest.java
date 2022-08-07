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
    final List<Integer> tokenTypes =
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF);

    testLexerTokenTypes(plainTextString, tokenTypes);

    Assertions.assertEquals(5, getResultsFromXPATH(plainTextString, "//TEXT").size());

    testParseTreeString(
        plainTextString, "([] ([6] This) ([7] is) ([7] just) ([7] plain) ([7] text))");
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

    testParseTreeString(stringWithHeaders, "([] ([6] ([13 6] == ([23 13 6] Header) ==)))");
  }

  @Test
  void horizontalRulesAreRecognized() {
    final String stringWithHorizontalRule = "Some text\n----More text";
    testLexerTokenTypes(
        stringWithHorizontalRule,
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.HORIZONTAL_RULE,
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF));

    Assertions.assertEquals(4, getResultsFromXPATH(stringWithHorizontalRule, "//TEXT").size());
    Assertions.assertEquals(0, getResultsFromXPATH(stringWithHorizontalRule, "//header").size());
    Assertions.assertEquals(
        1, getResultsFromXPATH(stringWithHorizontalRule, "//HORIZONTAL_RULE").size());

    testParseTreeString(
        stringWithHorizontalRule, "([] ([6] Some) ([7] text) ([7] ----) ([7] More) ([7] text))");
  }
}
