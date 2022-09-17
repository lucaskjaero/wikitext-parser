package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TemplateParameterSubstituter {
  private static final Pattern PARAMETER_WITH_DEFAULT_REGEX =
      Pattern.compile("\\{\\{\\{([^{}|]+\\|)([^}]*)}}}", Pattern.DOTALL);
  private static final String PARAMETER_REPLACEMENT_REGEX = "\\{\\{\\{%s\\|?\\}\\}\\}";

  /**
   * Substitutes the parameters into the template.
   *
   * @param input The template text
   * @param positionalParameters Parameters specified by position.
   * @param namedParameters Parameters specified by name.
   * @return The filled out template.
   */
  public String evaluateTemplate(
      String input, List<String> positionalParameters, Map<String, String> namedParameters) {
    Map<String, String> parameters = evaluateParameterValues(positionalParameters, namedParameters);

    String temp = input;
    for (int i = 0; i < 100; i++) {
      String result = replaceParametersIteration(temp, parameters);
      if (temp.equals(result)) {
        return temp;
      } else {
        temp = result;
      }
    }

    return temp;
  }

  private String replaceParametersIteration(String input, Map<String, String> parameters) {
    return replaceDefaultParameters(replaceParameters(input, parameters));
  }

  private String replaceParameters(String input, Map<String, String> parameters) {
    return parameters.keySet().stream()
        .reduce(
            input,
            (processed, parameterValue) ->
                processed.replaceAll(
                    String.format(PARAMETER_REPLACEMENT_REGEX, parameterValue),
                    parameters.getOrDefault(parameterValue, "")));
  }

  private String replaceDefaultParameters(String input) {
    return PARAMETER_WITH_DEFAULT_REGEX.matcher(input).replaceAll(result -> result.group(2));
  }

  /**
   * Combines the named and positional parameters into one lookup table for substitution.
   *
   * @param positionalParameters Parameters that are specified by position.
   * @param namedParameters Parameters that are specified by name.
   * @return The lookup table.
   */
  private static Map<String, String> evaluateParameterValues(
      List<String> positionalParameters, Map<String, String> namedParameters) {
    Map<String, String> evaluatedParameters = new HashMap<>(namedParameters);

    // Translate positional parameters to named ones for the table.
    for (int i = 0; i < positionalParameters.size(); i++) {
      evaluatedParameters.put(String.valueOf(i + 1), positionalParameters.get(i));
    }

    return evaluatedParameters;
  }
}
