package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import com.lucaskjaerozhang.wikitext_parser.common.metadata.WikiLinkEvaluator;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    checkParameterCount(ANCHOR_ENCODE, parameters, 1);
    return Optional.of(parameters.get(0).replace(" ", "_"));
  }

  public static Optional<String> canonicalUrl(List<String> parameters) {
    // TODO need to implement better URL handling logic
    checkParameterCount(CANONICAL_URL, parameters, 1, 3);
    if (parameters.size() == 1) return WikiLinkEvaluator.evaluateLink("wiki", parameters.get(0));

    String queryString = parameters.get(1);
    return WikiLinkEvaluator.evaluateLink("wiki", parameters.get(0))
        .map(prefix -> String.format("%s?%s", prefix, queryString));
  }

  public static Optional<String> filePath(List<String> parameters) {
    // TODO need to implement better URL handling logic
    return Optional.empty();
  }

  public static Optional<String> fullUrl(List<String> parameters) {
    // TODO need to implement better URL handling logic
    return Optional.empty();
  }

  public static Optional<String> localUrl(List<String> parameters) {
    checkParameterCount(LOCAL_URL, parameters, 1, 2);
    String queryString = parameters.size() == 2 ? String.format("?%s", parameters.get(1)) : "";
    return Optional.of(String.format("/wiki/%s%s", parameters.get(0), queryString));
  }

  public static Optional<String> urlEncode(List<String> parameters) {
    checkParameterCount(URL_ENCODE, parameters, 1, 2);
    String text = parameters.get(0);
    String spaceFlag = parameters.size() == 2 ? parameters.get(1) : "QUERY";

    try {
      String encoded = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
      return switch (spaceFlag) {
        case "PATH" -> Optional.of(encoded);
        case "QUERY" -> Optional.of(encoded.replace("%20", "+"));
        case "WIKI" -> Optional.of(encoded.replace("%20", "_"));
        default -> throw new IllegalArgumentException(
            String.format(
                "Unsupported urlencode type %s, supported options: PATH, QUERY, WIKI", spaceFlag));
      };
    } catch (UnsupportedEncodingException e) {
      return Optional.empty();
    }
  }
}
