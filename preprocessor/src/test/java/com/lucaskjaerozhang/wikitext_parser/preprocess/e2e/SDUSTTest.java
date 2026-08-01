package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class SDUSTTest extends PreprocessorEndToEndTest {
  public SDUSTTest() {
    super("wikipedia", "zh", "山东科技大学");
  }

  @Test
  void sdustTest() {
    endToEndTest();
  }

  @Test
  void parserFunctionParametersCanContainChineseLanguageConversionMarkup() {
    testPreprocessorWithString(
        "{{#if:|ignored|[[Wikipedia:失效链接|永久-{zh:失效連結;zh-cn:失效链接}-]]}}",
        "[[Wikipedia:失效链接|永久-{zh:失效連結;zh-cn:失效链接}-]]");
  }

  @Test
  void externalLinksDoNotLeakPipesToContainingParserFunctions() {
    testPreprocessorWithString(
        "{{#if:|[http://web.archive.org/web/*/ <span>-{zh:清理;zh-cn:清理}-</span>]|[[Wikipedia:失效链接|dead link]]}}",
        "[[Wikipedia:失效链接|dead link]]");
  }

  @Test
  void externalLinkParsingDoesNotConsumeWikiLinks() {
    testPreprocessorWithString(
        "{{#if:1|[[Template:Short description]]}}", "[[Template:Short description]]");
  }
}
