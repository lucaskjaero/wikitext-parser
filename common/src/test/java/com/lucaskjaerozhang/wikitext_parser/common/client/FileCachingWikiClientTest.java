package com.lucaskjaerozhang.wikitext_parser.common.client;

import static org.mockito.Mockito.*;

import com.lucaskjaerozhang.wikitext_parser.common.client.responses.WikiPage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileCachingWikiClientTest {
  private static final String JUPITER = "Jupiter";
  private static final Path CACHE_DIRECTORY_FOR_TEST =
      Path.of(".").resolve("source").resolve("fakewiki").resolve("en");
  private static final Path MAPPING_FILE = CACHE_DIRECTORY_FOR_TEST.resolve("mapping.json");

  private final WikiRestClient restClient = mock(WikiRestClient.class);
  private final FileCachingWikiClient testClient =
      FileCachingWikiClient.builder()
          .sourceClient(restClient)
          .wiki("fakewiki")
          .cacheDirectory(".")
          .build();

  @Test
  void canCacheResults() throws IOException {
    // Makes sure we always start with a clean test environment, no matter what happened before.
    if (Files.exists(MAPPING_FILE)) {
      Files.delete(MAPPING_FILE);
    }

    WikiPage expected = createTestWiki();
    when(restClient.getPageSource(JUPITER)).thenReturn(Optional.of(expected));

    // Cache miss
    Optional<WikiPage> jupiter = testClient.getPageSource(JUPITER);
    Assertions.assertTrue(jupiter.isPresent());
    Assertions.assertEquals(expected, jupiter.get());

    // Cache hit
    Optional<WikiPage> jupiterCached = testClient.getPageSource(JUPITER);
    Assertions.assertTrue(jupiterCached.isPresent());
    Assertions.assertEquals(expected.getId(), jupiterCached.get().getId());

    // Confirm cache is working
    verify(restClient, times(1)).getPageSource(JUPITER);
    Assertions.assertTrue(Files.exists(MAPPING_FILE));

    Map<String, String> mapping =
        FileCachingWikiClient.getOrUpdateArticleMappingTable(
            MAPPING_FILE, Optional.empty(), Optional.empty());
    Path testResult = CACHE_DIRECTORY_FOR_TEST.resolve(mapping.get(JUPITER));
    Assertions.assertTrue(Files.exists(testResult));

    // And then clean up
    Files.delete(MAPPING_FILE);
    Files.delete(testResult);
  }

  private WikiPage createTestWiki() {
    WikiPage result = new WikiPage();
    result.setId(42);
    result.setKey(JUPITER);
    result.setTitle(JUPITER);

    WikiPage.LatestRevision latestRevision = new WikiPage.LatestRevision();
    latestRevision.setId(42);
    latestRevision.setTimestamp("today");
    result.setLatest(latestRevision);

    result.setContentModel("wikitext");

    WikiPage.License license = new WikiPage.License();
    license.setTitle("Free");
    license.setUrl("url");
    result.setLicense(license);

    result.setHtmlUrl("htmlurl");
    result.setSource("source");

    return result;
  }
}
