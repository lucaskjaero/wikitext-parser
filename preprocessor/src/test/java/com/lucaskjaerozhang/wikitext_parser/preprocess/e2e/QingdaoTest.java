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
}
