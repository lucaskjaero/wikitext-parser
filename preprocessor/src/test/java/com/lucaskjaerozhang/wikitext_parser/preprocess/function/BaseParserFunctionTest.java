package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.DummyTemplateProvider;
import org.junit.jupiter.api.Assertions;

public abstract class BaseParserFunctionTest {
  public static void testParserFunction(String input, String expected) {

    Preprocessor preprocessor =
        Preprocessor.builder().templateProvider(new DummyTemplateProvider()).build();
    String result = preprocessor.preprocess(input, true);
    Assertions.assertEquals(expected, result);
  }
}
