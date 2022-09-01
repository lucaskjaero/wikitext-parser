package com.lucaskjaerozhang.wikitext_parser.common.client;

import com.google.gson.Gson;
import com.lucaskjaerozhang.wikitext_parser.common.client.responses.WikiPage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
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
    String filename =
        String.format("/%s/%s/%s/%s.json", PAGE_SOURCE_FOLDER, wiki, language, pageTitle);
    createCacheFolderStructure(PAGE_SOURCE_FOLDER, wiki, language);
    return getCached(() -> sourceClient.getPageSource(pageTitle), filename, WikiPage.class);
  }

  private <T> Optional<T> getCached(
      Callable<Optional<T>> function, String filename, Class<T> clazz) {
    Path path = Path.of(String.format("%s/%s", cacheDirectory, filename));
    // Happy path: it's already cached, so we just return it.
    if (Files.exists(path)) {
      try {
        return Optional.of(gson.fromJson(Files.readString(path), clazz));
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
        Files.writeString(path, gson.toJson(fetched.get()));
      } catch (IOException e) {
        System.err.printf("Failed to write to cache due to error: %s", e.getMessage());
      }
    }

    return fetched;
  }

  private void createCacheFolderStructure(String method, String wiki, String language) {
    try {
      createFolder(String.format("%s/%s", cacheDirectory, method));
      createFolder(String.format("%s/%s/%s", cacheDirectory, method, wiki));
      createFolder(String.format("%s/%s/%s/%s", cacheDirectory, method, wiki, language));
    } catch (IOException e) {
      System.err.printf(
          "Failed to create cache directory, caching will not work. Error: %s", e.getMessage());
    }
  }

  private void createFolder(String folderPath) throws IOException {
    Path path = Path.of(folderPath);
    if (!Files.exists(path)) {
      Files.createDirectory(path);
    }
  }
}
