package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ParserFunctionEvaluator extends BaseFunctionEvaluator {
  private static final String INVOKE = "#invoke";
  private static final String LOWERCASE_FUNCTION = "lc";
  private static final String PLURAL_FUNCTION = "plural";

  public static Optional<String> evaluateFunction(String functionName, List<String> parameters) {
    return switch (functionName) {
      case ExtensionParserFunctionEvaluator.EXPRESSION -> ExtensionParserFunctionEvaluator.expr(
          parameters);
      case ExtensionParserFunctionEvaluator.IF -> ExtensionParserFunctionEvaluator.ifFunction(
          parameters);
      case ExtensionParserFunctionEvaluator.IF_EQ -> ExtensionParserFunctionEvaluator.ifEq(
          parameters);
      case ExtensionParserFunctionEvaluator.IF_EXPRESSION -> ExtensionParserFunctionEvaluator
          .ifExpression(parameters);
      case ExtensionParserFunctionEvaluator.IF_ERROR -> ExtensionParserFunctionEvaluator.ifError(
          parameters);
      case URLFunctionEvaluator.ANCHOR_ENCODE -> URLFunctionEvaluator.anchorEncode(parameters);
      case URLFunctionEvaluator.CANONICAL_URL -> URLFunctionEvaluator.canonicalUrl(parameters);
      case URLFunctionEvaluator.FILE_PATH -> URLFunctionEvaluator.filePath(parameters);
      case URLFunctionEvaluator.FULL_URL -> URLFunctionEvaluator.fullUrl(parameters);
      case URLFunctionEvaluator.LOCAL_URL -> URLFunctionEvaluator.localUrl(parameters);
      case INVOKE -> Optional.of(invoke(parameters));
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
