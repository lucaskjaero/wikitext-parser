package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

public class MoratoriumTest extends PreprocessorEndToEndTest {
  public MoratoriumTest() {
    super("wikipedia", "en", "Moratorium_(law)");
  }

  @Test
  void moratoriumTest() {
    endToEndTest();
  }

  @Test
  void shortDescriptionTest() {
    testPreprocessor(
        "{{Short description|Delay or suspension of an activity or a law}}", "short_description");
  }

  @Test
  void pageTypeTest() {
    testPreprocessor("{{pagetype |defaultns = extended |plural=y}}", "pagetype");
    testPreprocessor("{{pagetype |defaultns = all |user=exclude}}", "pagetype");
  }
}
