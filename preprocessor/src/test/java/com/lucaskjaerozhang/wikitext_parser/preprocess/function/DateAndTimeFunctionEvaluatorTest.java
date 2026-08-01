package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import org.junit.jupiter.api.Test;

class DateAndTimeFunctionEvaluatorTest extends BaseParserFunctionTest {
  @Test
  void testTimeFunctionWithDate() {
    testParserFunction(
        "{{#time:Y年n月|2017年12月}}",
        "<module name='time'><argument>Y年n月</argument><argument>2017年12月</argument></module>");
  }

  @Test
  void testTimeFunctionEvaluatesArgumentsBeforeBuildingShim() {
    testParserFunction(
        "{{#time:Y年n月|{{#invoke:Dead link|date|2017年12月}}}}",
        "<module name='time'><argument>Y年n月</argument><argument><module name='Dead link'><argument>date</argument><argument>2017年12月</argument></module></argument></module>");
  }

  @Test
  void testTimeFunctionWithoutDate() {
    testParserFunction("{{#time:U}}", "<module name='time'><argument>U</argument></module>");
  }
}
