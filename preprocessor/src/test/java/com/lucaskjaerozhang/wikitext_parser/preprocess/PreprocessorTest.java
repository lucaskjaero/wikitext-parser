package com.lucaskjaerozhang.wikitext_parser.preprocess;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PreprocessorTest {
  public static Preprocessor testPreprocessor(
      String input, String expected, Map<String, String> variables) {
    Preprocessor preprocessor = new Preprocessor(new PreprocessorVariables(variables));
    String result = preprocessor.preprocess(input);
    Assertions.assertEquals(expected, result);
    return preprocessor;
  }

  public static Preprocessor testPreprocessor(String input, String expected) {
    return testPreprocessor(input, expected, Map.of());
  }

  @Test
  void preprocessorIdentifiesBehaviorSwitches() {
    Preprocessor preprocessor = testPreprocessor("__TOC____NOEDITSECTION__", "");
    List<String> orderedBehaviorSwitches =
        preprocessor.getBehaviorSwitches().stream().sorted().toList();
    Assertions.assertIterableEquals(
        List.of("__NOEDITSECTION__", "__TOC__"), orderedBehaviorSwitches);
  }

  @Test
  void preprocessorFillsInVariables() {
    testPreprocessor("{{FULLPAGENAME}}", "pageName", Map.of("FULLPAGENAME", "pageName"));
  }

  @Test
  void preprocessorLeavesTemplatesAlone() {
    testPreprocessor("{{Documentation}}", "{{Documentation}}", Map.of("FULLPAGENAME", "pageName"));
  }

  @Test
  void preprocessorInvokesFunctionsWithOneParameter() {
    testPreprocessor("{{lc:UPPERCASE}}", "uppercase");
  }

  @Test
  void preprocessorInvokesFunctionsWithMultipleParameters() {
    testPreprocessor("{{plural:1|singular|plural}}", "singular");
    testPreprocessor("{{plural:2|singular|plural}}", "plural");
  }

  @Test
  void preprocessorLeavesUnimplementedFunctionsAlone() {
    testPreprocessor("{{unimplemented:UPPERCASE}}", "{{unimplemented:UPPERCASE}}");
  }

  @Test
  void preprocessorLeavesNowikiBlocksAlone() {
    testPreprocessor("<nowiki>{{lc:UPPERCASE}}<nowiki/>", "<nowiki>{{lc:UPPERCASE}}<nowiki/>");
  }
}
