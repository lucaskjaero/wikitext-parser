package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TemplateParameterSubstituter {
  private static final Pattern PARAMETER_WITH_DEFAULT_REGEX =
      Pattern.compile("\\{\\{\\{([^}^|]+\\|)([^}]*)}}}", Pattern.DOTALL);
  private static final String PARAMETER_REPLACEMENT_REGEX = "\\{\\{\\{%s\\|?\\}\\}\\}";

  /**
   * Substitutes the parameters into the template. Unspecified parameters are just removed.
   *
   * @param input The template text
   * @param positionalParameters Parameters specified by position.
   * @param namedParameters Parameters specified by name.
   * @return The filled out template.
   */
  public String evaluateTemplate(
      String input, List<String> positionalParameters, Map<String, String> namedParameters) {
    Map<String, String> parameters = evaluateParameterValues(positionalParameters, namedParameters);

    String providedParametersReplaced =
        parameters.keySet().stream()
            .reduce(
                input,
                (processed, parameterValue) ->
                    processed.replaceAll(
                        String.format(PARAMETER_REPLACEMENT_REGEX, parameterValue),
                        parameters.getOrDefault(parameterValue, "")));

    // Parameters can be specified with default format using the {{{name|default}}} syntax.
    // This applies the default if we haven't already replaced that parameter.
    return PARAMETER_WITH_DEFAULT_REGEX
        .matcher(providedParametersReplaced)
        .replaceAll(result -> result.group(2));
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
