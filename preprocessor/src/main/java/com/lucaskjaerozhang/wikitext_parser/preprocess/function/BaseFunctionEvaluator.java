package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.concurrent.Callable;

public class BaseFunctionEvaluator {
  protected static void checkParameterCount(
      String function, List<?> parameters, Integer requiredCount) {
    if (parameters.size() != requiredCount) {
      throw new IllegalArgumentException(
          String.format(
              "Wrong number of arguments passed to %s function, given: %s, required: %s.",
              function, parameters.size(), requiredCount));
    }
  }

  protected static void checkParameterCount(
      String function, List<?> parameters, Integer min, Integer max) {
    if (parameters.size() < min || parameters.size() > max) {
      throw new IllegalArgumentException(
          String.format(
              "Wrong number of arguments passed to %s function, given: %s, minimum: %s, maximum: %s.",
              function, parameters.size(), min, max));
    }
  }

  protected static void checkMinParameterCount(String function, List<?> parameters, Integer min) {
    if (parameters.size() < min) {
      throw new IllegalArgumentException(
          String.format(
              "Wrong number of arguments passed to %s function, given: %s, required minimum: %s.",
              function, parameters.size(), min));
    }
  }

  protected static List<String> visitAllParameters(List<Callable<String>> parameters) {
    return parameters.stream().map(BaseFunctionEvaluator::evaluate).toList();
  }

  protected static String evaluate(Callable<String> parameter) {
    try {
      return parameter.call();
    } catch (Exception e) {
      System.err.printf("Error during evaluating parameter: %s", e.getMessage());
      return "";
    }
  }
}
