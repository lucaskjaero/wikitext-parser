package com.lucaskjaerozhang.wikitext_parser.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CacheFileUtils {
  public static void createCacheFolderStructure(
      String cacheDirectory, String folder, String wiki, String language) {
    Path cd = Path.of(cacheDirectory);
    try {
      createFolder(cd.resolve(folder));
      createFolder(cd.resolve(folder).resolve(wiki));
      createFolder(cd.resolve(folder).resolve(wiki).resolve(language));
    } catch (IOException e) {
      System.err.printf(
          "Failed to create cache directory, caching will not work. Error: %s", e.getMessage());
    }
  }

  public static void createCacheFolderStructure(
      String cacheDirectory, String folder, String wiki, String language, String testName) {
    createCacheFolderStructure(cacheDirectory, folder, wiki, language);
    try {
      createFolder(
          Path.of(cacheDirectory)
              .resolve(folder)
              .resolve(wiki)
              .resolve(language)
              .resolve(testName));
    } catch (IOException e) {
      System.err.printf(
          "Failed to create cache directory, caching will not work. Error: %s", e.getMessage());
    }
  }

  public static void createFolder(Path path) throws IOException {
    if (!Files.exists(path)) {
      Files.createDirectory(path);
    }
  }
}
