package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class JupiterTest extends PreprocessorEndToEndTest {
  public JupiterTest() {
    super("wikipedia", "en", "jupiter");
  }

  @Test
  void jupiterTest() {
    endToEndTest();
  }
}
