package wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.WikiTextLexer;
import java.util.Arrays;
import java.util.List;

import com.lucaskjaerozhang.wikitext_parser.WikiTextParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wikitext_parser.util.TestErrorListener;

class WikiTextLexerAndParserTest {
  private WikiTextLexer getLexerFromString(String testString) {
    WikiTextLexer lexer = new WikiTextLexer(CharStreams.fromString(testString));
    lexer.addErrorListener(new TestErrorListener());
    return lexer;
  }

  private List<Integer> getTokenTypesFromLexer(WikiTextLexer lexer) {
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    tokens.fill();
    return tokens.getTokens().stream().map(Token::getType).toList();
  }

  private WikiTextParser getParserFromLexer(WikiTextLexer lexer) {
    WikiTextParser parser = new WikiTextParser(new CommonTokenStream(lexer));
    parser.addErrorListener(new TestErrorListener());
    return parser;
  }

  @Test
  void plainTextIsRecognized() {
    WikiTextLexer lexer = getLexerFromString("This is just plain text");

    List<Integer> tokenTypes = getTokenTypesFromLexer(lexer);
    List<Integer> assertAgainst =
            Arrays.asList(WikiTextLexer.TEXT, WikiTextLexer.TEXT, WikiTextLexer.TEXT, WikiTextLexer.TEXT, WikiTextLexer.TEXT, WikiTextLexer.EOF);
    Assertions.assertIterableEquals(tokenTypes, assertAgainst);

    WikiTextParser parser = getParserFromLexer(lexer);
    parser.
  }

  @Test
  void headersAreRecognized() {
    WikiTextLexer lexer = getLexerFromString("== Header ==");

    List<Integer> tokenTypes = getTokenTypesFromLexer(lexer);
    List<Integer> assertAgainst =
        Arrays.asList(
            WikiTextLexer.T__1, WikiTextLexer.TEXT, WikiTextLexer.T__1, WikiTextLexer.EOF);

    Assertions.assertIterableEquals(tokenTypes, assertAgainst);
  }
}
