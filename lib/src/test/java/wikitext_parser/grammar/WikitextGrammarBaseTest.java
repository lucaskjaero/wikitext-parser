package wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.WikiTextParser;
import java.util.Collection;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.junit.jupiter.api.Assertions;
import wikitext_parser.util.TestErrorListener;

public class WikitextGrammarBaseTest {
  /**
   * Tests that tokens are recognized correctly. Explicitly does not share the lexer back because
   * performing this test depletes the lexer. Doing anything else with the lexer after this test has
   * run will automatically fail.
   *
   * @param testString The string to test.
   * @param assertAgainst A collection of token types that should be recognized from the test
   *     string.
   */
  protected void testLexerTokenTypes(String testString, List<Integer> assertAgainst) {
    List<Integer> tokenTypes = getTokenTypesFromLexer(getLexerFromString(testString));
    Assertions.assertIterableEquals(tokenTypes, assertAgainst);
  }

  /**
   * Lets you test parsers by xpath. Lets you write slightly more targeted tests than by tree.
   *
   * @param testString The string to construct a parse tree from.
   * @param xpath The XPATH you want to use to find elements.
   * @return The elements found from the xpath.
   */
  protected Collection<ParseTree> getResultsFromXPATH(String testString, String xpath) {
    WikiTextParser parser = getParserFromString(testString);
    return XPath.findAll(parser.root(), xpath, parser);
  }

  /**
   * Simple parse test for getting the parse tree for the entire string. Lets you write simple
   * tests.
   *
   * @param testString The string to construct a parse tree from.
   * @param expectedParseTree What you expect the parse tree to be as a string.
   */
  protected void testParseTreeString(String testString, String expectedParseTree) {
    Assertions.assertEquals(
        expectedParseTree, getParserFromString(testString).root().toStringTree());
  }

  // Helpful construction methods below here

  /**
   * Sets up a test for the lexer.
   *
   * @param testString The string to create the lexer from.
   * @return A new lexer from the test string.
   */
  protected WikiTextLexer getLexerFromString(String testString) {
    WikiTextLexer lexer = new WikiTextLexer(CharStreams.fromString(testString));
    lexer.addErrorListener(new TestErrorListener());
    return lexer;
  }

  /**
   * Sets up a test for the parser.
   *
   * @param testString A string to construct the parser from.
   * @return A new parser.
   */
  protected WikiTextParser getParserFromString(String testString) {
    WikiTextParser parser =
        new WikiTextParser(new CommonTokenStream(getLexerFromString(testString)));
    parser.addErrorListener(new TestErrorListener());
    return parser;
  }

  // Utility methods down here.

  /**
   * Helps you test tokens, specifically that: 1. You have the expected number of tokens recognized.
   * 2. They are the right types. Using this method depletes the lexer, so you should not reuse it.
   *
   * @param lexer A fully constructed Lexer from the test string.
   * @return The types of the tokens it recognized.
   */
  protected List<Integer> getTokenTypesFromLexer(WikiTextLexer lexer) {
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    tokens.fill();
    return tokens.getTokens().stream().map(Token::getType).toList();
  }
}
