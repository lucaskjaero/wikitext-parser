package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import com.lucaskjaerozhang.wikitext_parser.common.CacheFileUtils;
import com.lucaskjaerozhang.wikitext_parser.common.client.FileCachingWikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.OnlineTemplateProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

abstract class PreprocessorEndToEndTest {
  private static final String CACHE_DIRECTORY = "src/test/resources";
  private final String wiki;
  private final String language;
  private final String articleName;
  private final WikiClient testClient;
  private final Preprocessor preprocessor;

  public PreprocessorEndToEndTest(String wiki, String language, String articleName) {
    this.wiki = wiki;
    this.language = language;
    this.articleName = articleName;

    CacheFileUtils.createCacheFolderStructure(CACHE_DIRECTORY, "e2e", wiki, language, articleName);

    testClient =
        FileCachingWikiClient.builder()
            .cacheDirectory(CACHE_DIRECTORY)
            .language(language)
            .wiki(wiki)
            .sourceClient(WikiRestClient.builder().wiki(wiki).language(language).build())
            .build();
    preprocessor =
        Preprocessor.builder()
            .variables(
                Map.of(
                    "PAGENAME",
                    "Moratorium",
                    "NAMESPACE",
                    "Template",
                    "NAMESPACEE",
                    "Template",
                    "NAMESPACENUMBER",
                    "0"))
            .templateProvider(new OnlineTemplateProvider(testClient))
            .build();
  }

  protected void endToEndTest() {
    String input =
        testClient
            .getPageSource(articleName)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        String.format("Failed to get article %s", articleName)))
            .getSource();

    testPreprocessorWithFile(input, articleName);
  }

  protected void testPreprocessorWithFile(String input, String expectedFileName) {
    String actual = preprocessor.preprocess(input, true);
    String expected = getExpectedForArticle(expectedFileName);

    Assertions.assertEquals(expected, actual);
  }

  protected void testPreprocessorWithString(String input, String expected) {
    Assertions.assertEquals(expected, preprocessor.preprocess(input, true));
  }

  private String getExpectedForArticle(String articleName) {
    Path path =
        Path.of(
            String.format(
                "%s/e2e/%s/%s/%s/%s.txt",
                CACHE_DIRECTORY, wiki, language, this.articleName, articleName));
    if (Files.exists(path)) {
      try {
        return Files.readString(path);
      } catch (IOException e) {
        System.err.println("Failed to open cached file, returning empty");
      }
    }
    return "";
  }
}
