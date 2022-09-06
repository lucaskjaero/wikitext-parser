package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Callable;

public class ParserFunctionEvaluator extends BaseFunctionEvaluator {
  private static final String INVOKE = "#invoke";
  private static final String LOWERCASE_FUNCTION = "lc";
  private static final String PLURAL_FUNCTION = "plural";

  public static Optional<String> evaluateFunction(
      String functionName, List<Callable<String>> parameters) {
    return switch (functionName) {
      case ExtensionParserFunctionEvaluator.EXPRESSION -> Optional.of(
          ExtensionParserFunctionEvaluator.expr(parameters));
      case ExtensionParserFunctionEvaluator.IF -> Optional.of(
          ExtensionParserFunctionEvaluator.ifFunction(parameters));
      case ExtensionParserFunctionEvaluator.IF_EQ -> Optional.of(
          ExtensionParserFunctionEvaluator.ifEq(parameters));
      case ExtensionParserFunctionEvaluator.IF_EXPRESSION -> Optional.of(
          ExtensionParserFunctionEvaluator.ifExpression(parameters));
      case ExtensionParserFunctionEvaluator.IF_ERROR -> Optional.of(
          ExtensionParserFunctionEvaluator.ifError(parameters));
      case URLFunctionEvaluator.ANCHOR_ENCODE -> Optional.of(
          URLFunctionEvaluator.anchorEncode(visitAllParameters(parameters)));
      case URLFunctionEvaluator.CANONICAL_URL -> Optional.of(
          URLFunctionEvaluator.canonicalUrl(visitAllParameters(parameters)));
      case URLFunctionEvaluator.LOCAL_URL -> Optional.of(
          URLFunctionEvaluator.localUrl(visitAllParameters(parameters)));
      case INVOKE -> Optional.of(invoke(visitAllParameters(parameters)));
      case LOWERCASE_FUNCTION -> Optional.of(lowercase(visitAllParameters(parameters)));
      case PLURAL_FUNCTION -> Optional.of(plural(visitAllParameters(parameters)));
      case URLFunctionEvaluator.URL_ENCODE -> Optional.of(
          URLFunctionEvaluator.urlEncode(visitAllParameters(parameters)));
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

  private static String invoke(List<String> parameters) {
    checkMinParameterCount(INVOKE, parameters, 1);
    String functionName = parameters.get(0);
    List<String> functionParameters =
        parameters.subList(1, parameters.size()).stream()
            .map(p -> String.format("<argument>%s</argument>", p))
            .toList();

    return String.format(
        "<module name='%s'>%s</module>", functionName, String.join("", functionParameters));
  }
}
