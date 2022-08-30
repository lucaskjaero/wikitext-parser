package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.PreprocessorVariables;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.DummyTemplateProvider;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlFunctionEvaluatorTest {
  public static void testParserFunction(String input, String expected) {
    Preprocessor preprocessor =
        new Preprocessor(new PreprocessorVariables(Map.of()), new DummyTemplateProvider());
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

  @Test
  void testLocalURL() {
    testParserFunction("{{localurl:MediaWiki}}", "/wiki/MediaWiki");
    testParserFunction("{{localurl:MediaWiki|printable=yes}}", "/wiki/MediaWiki?printable=yes");
  }

  @Test
  void testURLEncode() {
    testParserFunction("{{urlencode:x:y/z á é}}", "x%3Ay%2Fz+%C3%A1+%C3%A9");
    testParserFunction("{{urlencode:x:y/z á é|QUERY}}", "x%3Ay%2Fz+%C3%A1+%C3%A9");
    testParserFunction("{{urlencode:x:y/z á é|WIKI}}", "x%3Ay%2Fz_%C3%A1_%C3%A9");
    testParserFunction("{{urlencode:x:y/z á é|PATH}}", "x%3Ay%2Fz%20%C3%A1%20%C3%A9");
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> testParserFunction("{{urlencode:x:y/z á é|OTHER}}", "none"));
  }
}
