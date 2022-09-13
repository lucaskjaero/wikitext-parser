package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParserFunctionEvaluator extends BaseFunctionEvaluator {
  private static final String INVOKE = "#invoke";
  private static final String LOWERCASE_FUNCTION = "lc";
  private static final String PLURAL_FUNCTION = "plural";
  private static final String TAG = "#tag";

  private static final Pattern TAG_ATTRIBUTE_REGEX = Pattern.compile("([^=]+)=(.*)");

  public static Optional<String> evaluateFunction(
      String functionName, List<Callable<String>> parameters) {
    return switch (functionName) {
      case DateAndTimeFunctionEvaluator.CURRENTMONTH -> Optional.of(
          DateAndTimeFunctionEvaluator.currentMonth());
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
      case ExtensionParserFunctionEvaluator.SWITCH -> Optional.of(
          ExtensionParserFunctionEvaluator.switchExpression(parameters));
      case PathFunctionEvaluator.ANCHOR_ENCODE -> Optional.of(
          PathFunctionEvaluator.anchorEncode(visitAllParameters(parameters)));
      case PathFunctionEvaluator.CANONICAL_URL -> Optional.of(
          PathFunctionEvaluator.canonicalUrl(visitAllParameters(parameters)));
      case PathFunctionEvaluator.LOCAL_URL -> Optional.of(
          PathFunctionEvaluator.localUrl(visitAllParameters(parameters)));
      case PathFunctionEvaluator.NAMESPACE -> PathFunctionEvaluator.namespaceTranslator(
          visitAllParameters(parameters));
      case PathFunctionEvaluator.URL_ENCODE -> Optional.of(
          PathFunctionEvaluator.urlEncode(visitAllParameters(parameters)));
      case INVOKE -> Optional.of(invoke(visitAllParameters(parameters)));
      case LOWERCASE_FUNCTION -> Optional.of(lowercase(visitAllParameters(parameters)));
      case PLURAL_FUNCTION -> Optional.of(plural(visitAllParameters(parameters)));
      case TAG -> Optional.of(tag(visitAllParameters(parameters)));
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

  private static String tag(List<String> parameters) {
    checkMinParameterCount(TAG, parameters, 2);
    String tagName = parameters.get(0);
    String tagContent = parameters.get(1);

    if (parameters.size() == 2) {
      return String.format("<%s>%s</%s>", tagName, tagContent, tagName);
    }

    String attributes =
        parameters.subList(2, parameters.size()).stream()
            .map(TAG_ATTRIBUTE_REGEX::matcher)
            .map(
                m ->
                    m.results()
                        .map(r -> String.format("%s='%s'", r.group(1), r.group(2)))
                        .collect(Collectors.joining()))
            .collect(Collectors.joining(" "));
    return String.format("<%s %s>%s</%s>", tagName, attributes, tagContent, tagName);
  }
}
