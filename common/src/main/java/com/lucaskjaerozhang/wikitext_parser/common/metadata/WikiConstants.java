package com.lucaskjaerozhang.wikitext_parser.common.metadata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/** Metadata about wikis used during parsing. */
public class WikiConstants {
  private static final String CONSTANT_DIRECTORY = "/constants";
  private static final String CONSTANT_LOADING_ERROR_MESSAGE =
      "Failed to load constants file %s: %s";
  private static final Gson gson = new Gson();

  private static final Set<String> BEHAVIOR_SWITCHES =
      readSetConstant("behavior_switches.json", String.class);
  private static final Set<String> LANGUAGE_CODES =
      readSetOfStringsConstantToLowercase("language_codes.json");
  private static final Map<String, String> NAMESPACES =
      readMapConstant("namespaces.json", String.class, String.class);
  private static final Set<String> WIKIS = readSetOfStringsConstantToLowercase("wikis.json");

  public static boolean isBehaviorSwitch(String behaviorSwitch) {
    return BEHAVIOR_SWITCHES.contains(behaviorSwitch);
  }

  public static boolean isLanguageCode(String languageCode) {
    return LANGUAGE_CODES.contains(languageCode.toLowerCase(Locale.ROOT));
  }

  public static Optional<String> getNamespace(String namespace) {
    return NAMESPACES.containsKey(namespace)
        ? Optional.of(NAMESPACES.get(namespace))
        : Optional.empty();
  }

  public static boolean isWiki(String wiki) {
    return WIKIS.contains(wiki.toLowerCase(Locale.ROOT));
  }

  private static <K, V> Map<K, V> readMapConstant(
      String filename, Class<K> keyClass, Class<V> valueClass) {
    try {
      String constantString = readStringConstant(filename);
      TypeToken<Map<K, V>> token =
          (TypeToken<Map<K, V>>) TypeToken.getParameterized(Map.class, keyClass, valueClass);
      return gson.fromJson(constantString, token);
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(CONSTANT_LOADING_ERROR_MESSAGE, filename, e.getMessage()));
    }
  }

  private static Set<String> readSetOfStringsConstantToLowercase(String filename) {
    return readSetConstant(filename, String.class).stream()
        .map(String::toLowerCase)
        .collect(Collectors.toSet());
  }

  private static <T> Set<T> readSetConstant(String filename, Class<T> clazz) {
    try {
      String constantString = readStringConstant(filename);
      TypeToken<Set<T>> token = (TypeToken<Set<T>>) TypeToken.getParameterized(Set.class, clazz);
      return gson.fromJson(constantString, token);
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(CONSTANT_LOADING_ERROR_MESSAGE, filename, e.getMessage()));
    }
  }

  private static String readStringConstant(String filename) {
    String path = String.format("%s/%s", CONSTANT_DIRECTORY, filename);
    try {
      InputStream input =
          Optional.ofNullable(WikiConstants.class.getResourceAsStream(path))
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          String.format("Failed to find constants file %s", filename)));
      return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining());
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(CONSTANT_LOADING_ERROR_MESSAGE, filename, e.getMessage()));
    }
  }
}
