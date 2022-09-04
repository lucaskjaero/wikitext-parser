package com.lucaskjaerozhang.wikitext_parser.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CacheFileUtils {
  public static void createCacheFolderStructure(
      String cacheDirectory, String folder, String wiki, String language) {
    try {
      createFolder(String.format("%s/%s", cacheDirectory, folder));
      createFolder(String.format("%s/%s/%s", cacheDirectory, folder, wiki));
      createFolder(String.format("%s/%s/%s/%s", cacheDirectory, folder, wiki, language));
    } catch (IOException e) {
      System.err.printf(
          "Failed to create cache directory, caching will not work. Error: %s", e.getMessage());
    }
  }

  public static void createFolder(String folderPath) throws IOException {
    Path path = Path.of(folderPath);
    if (!Files.exists(path)) {
      Files.createDirectory(path);
    }
  }
}
