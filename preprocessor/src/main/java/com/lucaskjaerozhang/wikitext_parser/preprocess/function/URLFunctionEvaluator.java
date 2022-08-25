package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.Optional;

/** Implements URL functions from here: https://www.mediawiki.org/wiki/Help:Magic_words#URL_data */
public class URLFunctionEvaluator extends BaseFunctionEvaluator {
  public static final String ANCHOR_ENCODE = "anchorencode";
  public static final String CANONICAL_URL = "canonicalurl";
  public static final String FILE_PATH = "filepath";
  public static final String FULL_URL = "fullurl";
  public static final String LOCAL_URL = "localurl";
  public static final String URL_ENCODE = "urlencode";

  public static Optional<String> anchorEncode(List<String> parameters) {
    return Optional.empty();
  }

  public static Optional<String> canonicalUrl(List<String> parameters) {
    return Optional.empty();
  }

  public static Optional<String> filePath(List<String> parameters) {
    return Optional.empty();
  }

  public static Optional<String> fullUrl(List<String> parameters) {
    return Optional.empty();
  }

  public static Optional<String> localUrl(List<String> parameters) {
    return Optional.empty();
  }

  public static Optional<String> urlEncode(List<String> parameters) {
    return Optional.empty();
  }
}
