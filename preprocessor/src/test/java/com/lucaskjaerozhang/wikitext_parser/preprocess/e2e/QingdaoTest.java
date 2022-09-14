package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class QingdaoTest extends PreprocessorEndToEndTest {
  public QingdaoTest() {
    super("wikipedia", "zh", "青岛市");
  }

  @Test
  void qingdaoTest() {
    endToEndTest();
  }

  @Test
  void nsReferences() {
    testPreprocessorWithFile("{{nsreferences|group=註}}", "nsreferences");
  }

  @Test
  void nsReferencesTag() {
    testPreprocessorWithString(
        "{{#tag:ref|<div style=\"margin-left:-2.7em;margin-bottom:-1.5em\" class=\"noprint\">{{{group}}}:</div>|group={{{group}}}\u2060|follow=NoSpaceReferences_Prefix_{{#time:U}}}}",
        "<ref group='{{{group}}}\u2060' follow='NoSpaceReferences_Prefix_{{#time:U}}'><div style=\"margin-left:-2.7em;margin-bottom:-1.5em\" class=\"noprint\">{{{group}}}:</div></ref>");
  }
}
