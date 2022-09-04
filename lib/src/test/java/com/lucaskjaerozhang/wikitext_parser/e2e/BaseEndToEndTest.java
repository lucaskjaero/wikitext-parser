package com.lucaskjaerozhang.wikitext_parser.e2e;

import com.lucaskjaerozhang.wikitext_parser.TestErrorListener;
import com.lucaskjaerozhang.wikitext_parser.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.common.CacheFileUtils;
import com.lucaskjaerozhang.wikitext_parser.common.client.FileCachingWikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.OnlineTemplateProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;

public class BaseEndToEndTest extends WikitextBaseTest {
  private static final String CACHE_DIRECTORY = "src/test/resources";

  protected void endToEndTest(String articleName, String wiki, String language) {
    final WikiClient testClient =
        FileCachingWikiClient.builder()
            .cacheDirectory(CACHE_DIRECTORY)
            .sourceClient(WikiRestClient.builder().wiki(wiki).language(language).build())
            .build();

    String input =
        testClient
            .getPageSource(articleName)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        String.format("Failed to get article %s", articleName)))
            .getSource();

    String expected = getExpectedForArticle(articleName, wiki, language);

    WikiTextNode root =
        ParseTreeBuilder.visitTreeFromText(
            input, new OnlineTemplateProvider(testClient), List.of(new TestErrorListener()), true);
    String actual = WikiTextParser.writeToString(root);

    Assertions.assertEquals(expected, actual);
  }

  private static String getExpectedForArticle(String articleName, String wiki, String language) {
    CacheFileUtils.createCacheFolderStructure(CACHE_DIRECTORY, "e2e", wiki, language);
    Path path =
        Path.of(String.format("%s/e2e/%s/%s/%s.xml", CACHE_DIRECTORY, wiki, language, articleName));
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
