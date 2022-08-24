package com.lucaskjaerozhang.wikitext_parser.preprocess;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ParserFunctionEvaluator {
  private static final String LOWERCASE_FUNCTION = "lc";
  private static final String PLURAL_FUNCTION = "plural";

  public static Optional<String> evaluateFunction(String functionName, List<String> parameters) {
    return switch (functionName) {
      case LOWERCASE_FUNCTION -> Optional.of(lowercase(parameters));
      case PLURAL_FUNCTION -> Optional.of(plural(parameters));
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

  private static void checkParameterCount(
      String function, List<String> parameters, Integer requiredCount) {
    if (parameters.size() != requiredCount) {
      throw new IllegalArgumentException(
          String.format(
              "Wrong number of arguments passed to %s function, given: %s, required: %s.",
              function, parameters.size(), requiredCount));
    }
  }
}
