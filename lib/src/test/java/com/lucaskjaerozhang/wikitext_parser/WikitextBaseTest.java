package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import java.util.Collection;
import java.util.List;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.junit.jupiter.api.Assertions;

/**
 * Shared test framework for all grammar types. Grammar specification is defined in
 * https://en.wikipedia.org/wiki/Help:Wikitext The specification is conveniently broken into
 * sections, so I break the tests down in the same way.
 */
public class WikitextBaseTest {
  /**
   * Tests that tokens are recognized correctly. Helps you test tokens, specifically that:<br>
   * 1. You have the expected number of tokens recognized.<br>
   * 2. They are the right types.
   *
   * @param testString The string to test.
   * @param assertAgainst A collection of token types that should be recognized from the test
   *     string.
   */
  protected void testLexerTokenTypes(String testString, List<Integer> assertAgainst) {
    CommonTokenStream tokens = new CommonTokenStream(getLexerFromString(testString));
    tokens.fill();

    List<Integer> tokenTypes = tokens.getTokens().stream().map(Token::getType).toList();
    Assertions.assertIterableEquals(assertAgainst, tokenTypes);
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
    WikiTextParser parser = getParserFromString(testString);
    WikiTextParser.RootContext root = parser.root();
    // Passing the parser back in to turn state numbers to human-readable labels.
    String tree = root.toStringTree(parser);
    Assertions.assertEquals(expectedParseTree, tree);
  }

  protected void testTranslation(String testInput, String expectedXML) {
    WikiTextNode root =
        (WikiTextNode)
            ParseTreeBuilder.visitTreeFromText(testInput, List.of(new TestErrorListener()), true);
    Assertions.assertEquals(
        expectedXML, com.lucaskjaerozhang.wikitext_parser.WikiTextParser.writeToString(root));
  }

  // Helpful construction methods below here

  /**
   * Sets up a test for the lexer.
   *
   * @param testString The string to create the lexer from.
   * @return A new lexer from the test string.
   */
  protected WikiTextLexer getLexerFromString(String testString) {
    return ParseTreeBuilder.getLexerFromText(
        testString, List.of(new DiagnosticErrorListener(), new TestErrorListener()));
  }

  /**
   * Sets up a test for the parser.
   *
   * @param testString A string to construct the parser from.
   * @return A new parser.
   */
  protected WikiTextParser getParserFromString(String testString) {
    WikiTextParser parser =
        ParseTreeBuilder.getParserFromText(testString, List.of(new TestErrorListener()), true);
    // Really dig in deep to find ambiguities.
    parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
    return parser;
  }
}
