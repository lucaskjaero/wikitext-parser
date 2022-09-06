package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import org.junit.jupiter.api.Test;

public class ExtensionParserFunctionEvaluatorTest extends BaseParserFunctionTest {
  @Test
  void testSwitch() {
    final String input =
        """
              {{#switch:1
              |1 |1st |First=D. C.
              |2 |2nd |Second=F.
              |Supplement=Herbert Tredwell
              |#default=D. C.
              }}""";
    testParserFunction(input, input);
  }
}
