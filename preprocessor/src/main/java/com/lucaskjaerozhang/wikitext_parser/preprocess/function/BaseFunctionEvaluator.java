package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;

public class BaseFunctionEvaluator {
  protected static void checkParameterCount(
      String function, List<String> parameters, Integer requiredCount) {
    if (parameters.size() != requiredCount) {
      throw new IllegalArgumentException(
          String.format(
              "Wrong number of arguments passed to %s function, given: %s, required: %s.",
              function, parameters.size(), requiredCount));
    }
  }
}
