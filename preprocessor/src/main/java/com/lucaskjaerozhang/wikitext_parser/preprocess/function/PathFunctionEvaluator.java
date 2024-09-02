package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import com.lucaskjaerozhang.wikitext_parser.common.metadata.WikiConstants;
import com.lucaskjaerozhang.wikitext_parser.common.metadata.WikiLinkEvaluator;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * Implements URL functions from here: <a
 * href="https://www.mediawiki.org/wiki/Help:Magic_words#URL_data">...</a>
 */
public class PathFunctionEvaluator extends BaseFunctionEvaluator {
  public static final String ANCHOR_ENCODE = "anchorencode";
  public static final String CANONICAL_URL = "canonicalurl";
  public static final String FILE_PATH = "filepath";
  public static final String FULL_URL = "fullurl";
  public static final String LOCAL_URL = "localurl";
  public static final String NAMESPACE = "ns";
  public static final String URL_ENCODE = "urlencode";

  public static String anchorEncode(List<String> parameters) {
    checkParameterCount(ANCHOR_ENCODE, parameters, 1);
    return parameters.get(0).replace(" ", "_");
  }

  public static String canonicalUrl(List<String> parameters) {
    // TODO need to implement better URL handling logic
    checkParameterCount(CANONICAL_URL, parameters, 1, 3);

    String baseUrl =
        WikiLinkEvaluator.evaluateLink("mediawikiwiki", parameters.get(0))
            .orElse(parameters.get(0));

    return parameters.size() == 1 ? baseUrl : String.format("%s?%s", baseUrl, parameters.get(1));
  }

  public static String localUrl(List<String> parameters) {
    checkParameterCount(LOCAL_URL, parameters, 1, 2);
    String queryString = parameters.size() == 2 ? String.format("?%s", parameters.get(1)) : "";
    return String.format("/wiki/%s%s", parameters.get(0), queryString);
  }

  public static Optional<String> namespaceTranslator(List<String> parameters) {
    checkParameterCount(LOCAL_URL, parameters, 1, 1);
    return WikiConstants.getNamespace(parameters.get(0));
  }

  public static String urlEncode(List<String> parameters) {
    checkParameterCount(URL_ENCODE, parameters, 1, 2);
    String text = parameters.get(0);
    String spaceFlag = parameters.size() == 2 ? parameters.get(1) : "QUERY";

    String encoded = URLEncoder.encode(text, StandardCharsets.UTF_8);
    return switch (spaceFlag.toLowerCase()) {
      case "path" -> encoded.replace("+", "%20");
      case "query" -> encoded;
      case "wiki" -> encoded.replace("+", "_");
      default ->
          throw new IllegalArgumentException(
              String.format(
                  "Unsupported urlencode type %s, supported options: PATH, QUERY, WIKI",
                  spaceFlag));
    };
  }
}
