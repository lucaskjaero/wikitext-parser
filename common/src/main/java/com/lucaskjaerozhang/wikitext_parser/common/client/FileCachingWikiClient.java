package com.lucaskjaerozhang.wikitext_parser.common.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucaskjaerozhang.wikitext_parser.common.CacheFileUtils;
import com.lucaskjaerozhang.wikitext_parser.common.client.responses.WikiPage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import lombok.Builder;

@Builder
public class FileCachingWikiClient implements WikiClient {
  @Builder.Default private final String cacheDirectory = "src/main/resources";
  private static final String PAGE_SOURCE_FOLDER = "source";
  @Builder.Default private final String wiki = "wikipedia";
  @Builder.Default private final String language = "en";
  private final WikiClient sourceClient;
  private static final Gson gson = new Gson();

  @Override
  public Optional<WikiPage> getPageSource(String pageTitle) {
    Path methodCacheDirectory = getCacheDirectory(PAGE_SOURCE_FOLDER);
    CacheFileUtils.createCacheFolderStructure(cacheDirectory, PAGE_SOURCE_FOLDER, wiki, language);
    return getCached(
        () -> sourceClient.getPageSource(pageTitle),
        methodCacheDirectory,
        pageTitle,
        WikiPage.class);
  }

  private Path getCacheDirectory(String method) {
    return Path.of(cacheDirectory).resolve(method).resolve(wiki).resolve(language);
  }

  private <T> Optional<T> getCached(
      Callable<Optional<T>> function, Path methodCacheDirectory, String key, Class<T> clazz) {
    Path mappingFile = methodCacheDirectory.resolve("mapping.json");
    Optional<Path> path = getMappingForArticle(methodCacheDirectory, mappingFile, key);
    // Happy path: it's already cached, so we just return it.
    if (path.isPresent() && Files.exists(path.get())) {
      try {
        return Optional.of(gson.fromJson(Files.readString(path.get()), clazz));
      } catch (IOException e) {
        System.err.println("Failed to open cached file, falling back to source");
      }
    }

    // Cache miss
    Optional<T> fetched = Optional.empty();
    try {
      fetched = function.call();
    } catch (Exception ignored) {
      /* not really anything we can do */
    }

    // Put it in the cache
    if (fetched.isPresent()) {
      try {
        String writeName = createFilenameForArticle(mappingFile, key);
        Path writePath = methodCacheDirectory.resolve(writeName);
        Files.writeString(writePath, gson.toJson(fetched.get()));
      } catch (IOException e) {
        System.err.printf("Failed to write to cache due to error: %s", e.getMessage());
      }
    }

    return fetched;
  }

  private static Optional<Path> getMappingForArticle(
      Path cacheDirectoryPath, Path mappingFilePath, String article) {
    Map<String, String> mappingTable =
        getOrUpdateArticleMappingTable(mappingFilePath, Optional.empty(), Optional.empty());
    return mappingTable.containsKey(article)
        ? Optional.of(cacheDirectoryPath.resolve(mappingTable.get(article)))
        : Optional.empty();
  }

  private static String createFilenameForArticle(Path mappingFilePath, String article) {
    String articleFileName = String.format("%s-%s.json", article, UUID.randomUUID());
    getOrUpdateArticleMappingTable(
        mappingFilePath, Optional.of(article), Optional.of(articleFileName));
    return articleFileName;
  }

  /**
   * Single method to interact with the mapping table. This lets us just slap a synchronized on it
   * and not worry about race conditions. This does mean that all mapping tables block each other,
   * even when they're separate files. Hopefully this is rare enough that we don't need to optimize
   * for it.
   *
   * @param mappingFilePath The mapping table location.
   * @param key If updating the table, the article name.
   * @param value If updating the table, the article filename.
   * @return The mapping table.
   */
  public static synchronized Map<String, String> getOrUpdateArticleMappingTable(
      Path mappingFilePath, Optional<String> key, Optional<String> value) {
    Map<String, String> mappingTable = Map.of();
    if (!Files.exists(mappingFilePath)) {
      try {
        Files.writeString(mappingFilePath, gson.toJson(mappingTable));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    // We always need to get, even to update.
    try {
      mappingTable =
          gson.fromJson(
              Files.readString(mappingFilePath),
              new TypeToken<HashMap<String, String>>() {}.getType());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Update case.
    if (key.isPresent() && value.isPresent()) {
      mappingTable.put(key.get(), value.get());
      try {
        Files.writeString(mappingFilePath, gson.toJson(mappingTable));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return mappingTable;
  }
}
