package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.DummyTemplateProvider;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PreprocessorTest {
  public static Preprocessor testPreprocessor(
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
  void preprocessorInvokesFunctionsWithOneParameter() {
    testPreprocessor("{{lc:UPPERCASE}}", "uppercase");
  }

  @Test
  void preprocessorFunctionsCanManipulateXML() {
    final String input =
        """
                      {{#ifeq:yes|yes
                       |[[second|<span title="title" class="rt-commentedText" {{#ifeq:yes|no|
                        |style="border-bottom:1px dotted"
                       }}>second</span>]]
                       |<span title="title" class="rt-commentedText" {{#ifeq:yes|no|
                        |style="border-bottom:1px dotted"
                       }}>second</span>
                      }}
                      """;
    final String expected =
        """
            [[second|<span title="title" class="rt-commentedText" style="border-bottom:1px dotted">second</span>]]
            """;
    testPreprocessor(input, expected);
  }

  @Test
  void preprocessorLeavesUnimplementedFunctionsAlone() {
    testPreprocessor("{{unimplemented:UPPERCASE}}", "{{unimplemented:UPPERCASE}}");
  }

  @Test
  void preprocessorInvokesFunctionsWithMultipleParameters() {
    testPreprocessor("{{plural:1|singular|plural}}", "singular");
    testPreprocessor("{{plural:2|singular|plural}}", "plural");
  }

  @Test
  void preprocessorLeavesNowikiBlocksAlone() {
    testPreprocessor("<nowiki>{{lc:UPPERCASE}}<nowiki/>", "<nowiki>{{lc:UPPERCASE}}<nowiki/>");
  }

  @Test
  void preprocessorLeavesPlainTextAlone() {
    testPreprocessor("This is just plain text.", "This is just plain text.");
  }

  @Test
  void preprocessorCanHandleIfError() {
    testPreprocessor(
        "{{#iferror:{{#ifexpr: {{{1|1}}} > 1 }}|I errored|{{#switch:{{{1|}}}|1=|2=reflist-columns-2|#default=reflist-columns-3}} }}",
        "I errored");
  }

  @Test
  void preprocessorStripsComments() {
    testPreprocessor("<!-- I am a comment -->", "");
    testPreprocessor(
        "<!-- I am a comment -->I am not a comment<!-- I am also a comment -->",
        "I am not a comment");
    testPreprocessor(
        "<!-- I am a comment --><b>I am not a comment</b><!-- I am also a comment -->",
        "<b>I am not a comment</b>");
  }
}
