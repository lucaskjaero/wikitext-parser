package com.lucaskjaerozhang.wikitext_parser.e2e;

import org.junit.jupiter.api.Test;

class WikipediaTest extends BaseEndToEndTest {

  @Test
  void moratoriumIsCorrectlyParsed() {
    endToEndTest("Moratorium_(law)", "wikipedia", "en");
  }
}
