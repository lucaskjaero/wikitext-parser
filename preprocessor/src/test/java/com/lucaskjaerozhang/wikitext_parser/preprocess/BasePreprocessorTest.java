package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorLexer;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.DummyTemplateProvider;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Assertions;

public class BasePreprocessorTest {
  protected static Preprocessor testPreprocessor(
      String input, String expected, Map<String, String> variables) {
    Preprocessor preprocessor =
        Preprocessor.builder()
            .variables(variables)
            .templateProvider(new DummyTemplateProvider())
            .build();
    String result = preprocessor.preprocess(input, true);
    Assertions.assertEquals(expected, result);
    return preprocessor;
  }

  protected static Preprocessor testPreprocessor(String input, String expected) {
    return testPreprocessor(input, expected, Map.of());
  }

  protected void testLexerWithString(String input, List<Integer> expected) {
    WikiTextPreprocessorLexer lexer = new WikiTextPreprocessorLexer(CharStreams.fromString(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    tokens.fill();

    List<Integer> tokenTypes = tokens.getTokens().stream().map(Token::getType).toList();
    Assertions.assertIterableEquals(expected, tokenTypes);
  }
}
