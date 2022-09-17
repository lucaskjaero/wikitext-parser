package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class QingdaoTest extends PreprocessorEndToEndTest {
  public QingdaoTest() {
    super("wikipedia", "zh", "青岛市");
  }

  @Test
  void qingdaoTest() {
    //    endToEndTest();
  }

  @Test
  void nsReferences() {
    testPreprocessorWithFile("{{nsreferences|group=註}}", "nsreferences");
  }

  @Test
  void error() {
    String result =
        "<module name='Error'><argument>error</argument><argument>引用錯誤：group屬性不能為空</argument><argument>tag=</argument></module>";
    // Error template
    testPreprocessorWithString("{{error|引用錯誤：group屬性不能為空}}", result);
    // Resolves to this.
    testPreprocessorWithString("{{#invoke:Error|error|引用錯誤：group屬性不能為空|tag=}}", result);
  }

  @Test
  void ifNotEq() {
    testPreprocessorWithString(
        "<div id=\"references-NoSpaceReferences\">{{#tag:ref|<div style=\"margin-left:-2.7em;margin-bottom:-1.5em\" class=\"noprint\">註:</div>|group=註\u2060|follow=NoSpaceReferences_Prefix_{{#time:U}}}}{{#tag:references|{{{1}}}|group=註\u2060}}|{{error|引用錯誤：group屬性不能為空}}",
        "");
  }

  @Test
  void nsReferencesTag() {
    testPreprocessorWithString(
        "{{#tag:ref|<div style=\"margin-left:-2.7em;margin-bottom:-1.5em\" class=\"noprint\">{{{group}}}:</div>|group={{{group}}}\u2060|follow=NoSpaceReferences_Prefix_{{#time:U}}}}",
        "<ref group='{{{group}}}\u2060' follow='NoSpaceReferences_Prefix_{{#time:U}}'><div style=\"margin-left:-2.7em;margin-bottom:-1.5em\" class=\"noprint\">{{{group}}}:</div></ref>");
  }
}
