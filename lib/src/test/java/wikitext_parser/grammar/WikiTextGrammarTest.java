package wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class WikiTextGrammarTest extends WikitextGrammarBaseTest {

  @Test
  void plainTextIsRecognized() {
    final String plainTextString = "This is just plain text";

    testLexerTokenTypes(
        plainTextString,
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF));

    Assertions.assertEquals(5, getResultsFromXPATH(plainTextString, "//TEXT").size());

    testParseTreeString(
        plainTextString, "([] ([6] This) ([7] is) ([7] just) ([7] plain) ([7] text))");
  }

  @Test
  void headersAreRecognized() {
    final String stringWithHeaders = "== Header ==";
    testLexerTokenTypes(
        stringWithHeaders,
        Arrays.asList(
            WikiTextLexer.T__1, WikiTextLexer.TEXT, WikiTextLexer.T__1, WikiTextLexer.EOF));

    Assertions.assertEquals(1, getResultsFromXPATH(stringWithHeaders, "//TEXT").size());
    Assertions.assertEquals(1, getResultsFromXPATH(stringWithHeaders, "//header").size());

    testParseTreeString(stringWithHeaders, "([] ([6] ([13 6] == ([22 13 6] Header) ==)))");
  }
}
