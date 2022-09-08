package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class MoratoriumTest extends PreprocessorEndToEndTest {
  public MoratoriumTest() {
    super("wikipedia", "en", "Moratorium_(law)");
  }

  @Test
  void moratoriumTest() {
    endToEndTest();
  }

  @Test
  void shortDescriptionTest() {
    testPreprocessorWithFile(
        "{{Short description|Delay or suspension of an activity or a law}}", "short_description");
    testPreprocessorWithFile(
        "{{Short description|Delay or suspension of an activity or a law}}", "short_description");
  }

  @Test
  void pageTypeTest() {
    testPreprocessorWithString(
        "{{pagetype |defaultns = extended |plural=y}}", "{{safesubst:#invoke:pagetype|main}}");
    testPreprocessorWithString(
        "{{pagetype |defaultns = all |user=exclude}}", "{{safesubst:#invoke:pagetype|main}}");
  }

  @Test
  void mainOther() {
    testPreprocessorWithString("{{ns:0}}", "(Main/Article)");
    testPreprocessorWithString(
        """
            {{#if:\s
            | {{{demospace}}}    <!--Use lower case "demospace"-->
            | other\s
            }}""",
        "other");
    testPreprocessorWithString(
        "{{SDcat |sd=Delay or suspension of an activity or a law }}",
        "<module name='SDcat'><argument>setCat</argument></module>");
    testPreprocessorWithString(
        "{{Main other |{{SDcat |sd=Delay or suspension of an activity or a law }} }}", "");
  }
}
