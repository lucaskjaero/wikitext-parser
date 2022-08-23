package com.lucaskjaerozhang.wikitext_parser.parse;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateEvaluator {
  private static final Pattern PARAMETER_REGEX = Pattern.compile("\\{\\{\\{([^}]+)}}}");
  private static final String PARAMETER_REPLACEMENT_REGEX = "\\{\\{\\{%s\\}\\}\\}";

  public String evaluateTemplate(
      String input, List<String> positionalParameters, Map<String, String> namedParameters) {
    Set<String> requiredParameters = getTemplateParameterSubstitutions(input);

    // Bail out early if we can't evaluate the template.
    checkPositionalParameters(positionalParameters.size(), requiredParameters);
    checkNamedParameters(namedParameters.keySet(), requiredParameters);

    String withPositionalParametersEvaluated = input;
    for (int i = 0; i < positionalParameters.size(); i++) {
      String placeholder = String.format(PARAMETER_REPLACEMENT_REGEX, i + 1);
      String replacement = positionalParameters.get(i);
      withPositionalParametersEvaluated =
          withPositionalParametersEvaluated.replaceAll(placeholder, replacement);
    }

    String withNamedParametersEvaluated = withPositionalParametersEvaluated;
    for (Map.Entry<String, String> parameter : namedParameters.entrySet()) {
      withNamedParametersEvaluated =
          withNamedParametersEvaluated.replaceAll(
              String.format(PARAMETER_REPLACEMENT_REGEX, parameter.getKey()), parameter.getValue());
    }

    return withNamedParametersEvaluated;
  }

  private static Set<String> getTemplateParameterSubstitutions(String inputText) {
    Matcher matcher = PARAMETER_REGEX.matcher(inputText);
    return matcher.results().map(r -> r.group(1)).collect(Collectors.toUnmodifiableSet());
  }

  private static void checkPositionalParameters(
      Integer positionalParameterCount, Set<String> requiredParameters) {
    Optional<Integer> highestPositionalParameter =
        requiredParameters.stream()
            .filter(p -> p.matches("\\d"))
            .map(Integer::valueOf)
            .max(Integer::compareTo);
    if (highestPositionalParameter.isPresent()
        && highestPositionalParameter.get() > positionalParameterCount) {
      throw new IllegalArgumentException(
          String.format(
              "Template requires %s positional parameters and %s were provided.",
              highestPositionalParameter.get(), positionalParameterCount));
    }
  }

  private static void checkNamedParameters(
      Set<String> namedParameters, Set<String> requiredParameters) {
    Set<String> requiredNamedParameters =
        requiredParameters.stream().filter(p -> !p.matches("\\d")).collect(Collectors.toSet());

    if (!namedParameters.containsAll(requiredNamedParameters)) {
      String missingParameters =
          requiredNamedParameters.stream()
              .filter(p -> !namedParameters.contains(p))
              .map(m -> String.format("'%s'", m))
              .collect(Collectors.joining(", "));
      throw new IllegalArgumentException(
          String.format("Required named parameters %s not provided.", missingParameters));
    }
  }
}
