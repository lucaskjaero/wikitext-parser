package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.PreprocessorVariables;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlFunctionEvaluatorTest {
  public static void testParserFunction(String input, String expected) {
    Preprocessor preprocessor = new Preprocessor(new PreprocessorVariables(Map.of()));
    String result = preprocessor.preprocess(input, true);
    Assertions.assertEquals(expected, result);
  }

  @Test
  void testAnchorEncode() {
    testParserFunction("{{anchorencode:x y z á é}}", "x_y_z_á_é");
  }

  @Test
  void testCanonicalURL() {
    testParserFunction(
        "{{canonicalurl:Category:Top level}}", "https://www.mediawiki.org/wiki/Category:Top_level");
    testParserFunction(
        "{{canonicalurl:Category:Top level|action=edit}}",
        "https://www.mediawiki.org/wiki/Category:Top_level?action=edit");
  }
}
