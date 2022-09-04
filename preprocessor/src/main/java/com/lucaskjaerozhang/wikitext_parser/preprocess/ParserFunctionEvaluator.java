package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.preprocess.function.BaseFunctionEvaluator;
import com.lucaskjaerozhang.wikitext_parser.preprocess.function.ExtensionParserFunctionEvaluator;
import com.lucaskjaerozhang.wikitext_parser.preprocess.function.URLFunctionEvaluator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ParserFunctionEvaluator extends BaseFunctionEvaluator {
  private static final String INVOKE = "#invoke";
  private static final String LOWERCASE_FUNCTION = "lc";
  private static final String PLURAL_FUNCTION = "plural";

  public static Optional<String> evaluateFunction(String functionName, List<String> parameters) {
    return switch (functionName) {
      case ExtensionParserFunctionEvaluator.IF -> ExtensionParserFunctionEvaluator.ifFunction(
          parameters);
      case ExtensionParserFunctionEvaluator.IF_EQ -> ExtensionParserFunctionEvaluator.ifEq(
          parameters);
      case URLFunctionEvaluator.ANCHOR_ENCODE -> URLFunctionEvaluator.anchorEncode(parameters);
      case URLFunctionEvaluator.CANONICAL_URL -> URLFunctionEvaluator.canonicalUrl(parameters);
      case URLFunctionEvaluator.FILE_PATH -> URLFunctionEvaluator.filePath(parameters);
      case URLFunctionEvaluator.FULL_URL -> URLFunctionEvaluator.fullUrl(parameters);
      case URLFunctionEvaluator.LOCAL_URL -> URLFunctionEvaluator.localUrl(parameters);
        // TODO Let's not try to run lua functions.
      case INVOKE -> Optional.of("");
      case LOWERCASE_FUNCTION -> Optional.of(lowercase(parameters));
      case PLURAL_FUNCTION -> Optional.of(plural(parameters));
      case URLFunctionEvaluator.URL_ENCODE -> URLFunctionEvaluator.urlEncode(parameters);
      default -> Optional.empty();
    };
  }

  private static String lowercase(List<String> parameters) {
    checkParameterCount(LOWERCASE_FUNCTION, parameters, 1);
    return parameters.get(0).toLowerCase(Locale.ROOT);
  }

  private static String plural(List<String> parameters) {
    checkParameterCount(PLURAL_FUNCTION, parameters, 3);
    return parameters.get(0).equals("1") ? parameters.get(1) : parameters.get(2);
  }
}
