package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import org.junit.jupiter.api.Test;

class ExtensionParserFunctionEvaluatorTest extends BaseParserFunctionTest {
  @Test
  void testSwitch() {
    final String input =
        """
              {{#switch:%s
              |1 |1st |First=D. C.
              |#default=default
              |2 |2nd |Second=F.
              |Supplement=Herbert Tredwell
              |fake default
              }}""";
    testParserFunction(String.format(input, "1"), "D. C.");
    testParserFunction(String.format(input, "1st"), "D. C.");
    testParserFunction(String.format(input, "1st "), "D. C.");
    testParserFunction(String.format(input, "First"), "D. C.");

    testParserFunction(String.format(input, "2"), "F.");
    testParserFunction(String.format(input, "2nd"), "F.");
    testParserFunction(String.format(input, "Second"), "F.");

    testParserFunction(String.format(input, "Supplement"), "Herbert Tredwell");

    // Test default case
    testParserFunction(String.format(input, "anything"), "default");

    final String endIsDefault =
        """
              {{#switch:%s
              |1 |1st |First=D. C.
              |2 |2nd |Second=F.
              |Supplement=Herbert Tredwell
              |actual default
              }}""";
    testParserFunction(String.format(endIsDefault, "anything"), "actual default");
  }
}
