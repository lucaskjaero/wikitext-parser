package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class JupiterTest extends PreprocessorEndToEndTest {
  public JupiterTest() {
    super("wikipedia", "en", "jupiter");
  }

  @Test
  void jupiterTest() {
    //    endToEndTest();
  }

  @Test
  void redirectTest() {
    testPreprocessorWithString(
        "{{Sisterlinks}}",
        "<module name='Sister project links'><argument>main</argument></module>");
  }

  @Test
  void fixTemplateTest() {
    testPreprocessorWithFile(
        "{{Fix\n| link  = Wikipedia:Citing sources\n| title=<module name='delink'><argument>delink</argument></module>\n| text  = page&nbsp;needed\n| date  = \n| cat-date = Category:Wikipedia articles needing page number citations\n}}",
        "fix_citing");
  }
}
