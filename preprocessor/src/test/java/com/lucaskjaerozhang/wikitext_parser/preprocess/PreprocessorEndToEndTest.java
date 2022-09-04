package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.common.CacheFileUtils;
import com.lucaskjaerozhang.wikitext_parser.common.client.FileCachingWikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.OnlineTemplateProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PreprocessorEndToEndTest {
  private static final String CACHE_DIRECTORY = "src/test/resources";

  private void endToEndTest(String articleName, String wiki, String language) {
    final WikiClient testClient =
        FileCachingWikiClient.builder()
            .cacheDirectory(CACHE_DIRECTORY)
            .sourceClient(WikiRestClient.builder().wiki(wiki).language(language).build())
            .build();

    final Preprocessor preprocessor =
        Preprocessor.builder()
            .variables(
                Map.of("PAGENAME", "Moratorium", "NAMESPACE", "Template", "NAMESPACEE", "Template"))
            .templateProvider(new OnlineTemplateProvider(testClient))
            .build();

    String input =
        testClient
            .getPageSource(articleName)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        String.format("Failed to get article %s", articleName)))
            .getSource();

    String actual = preprocessor.preprocess(input, true);
    String expected = getExpectedForArticle(articleName, wiki, language);

    Assertions.assertEquals(expected, actual);
  }

  private static String getExpectedForArticle(String articleName, String wiki, String language) {
    CacheFileUtils.createCacheFolderStructure(CACHE_DIRECTORY, "e2e", wiki, language);
    Path path =
        Path.of(String.format("%s/e2e/%s/%s/%s.txt", CACHE_DIRECTORY, wiki, language, articleName));
    if (Files.exists(path)) {
      try {
        return Files.readString(path);
      } catch (IOException e) {
        System.err.println("Failed to open cached file, returning empty");
      }
    }
    return "";
  }

  @Test
  void moratoriumTest() {
    endToEndTest("Moratorium_(law)", "wikipedia", "en");
  }

  @Test
  void jupiterTest() {
    //    endToEndTest("Jupiter", "wikipedia", "en");
  }
}
