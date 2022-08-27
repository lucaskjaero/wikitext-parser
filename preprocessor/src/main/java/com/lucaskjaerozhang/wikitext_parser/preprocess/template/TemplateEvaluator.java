package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TemplateEvaluator {
  private static final Pattern NO_INCLUDE_REGEX =
      Pattern.compile("<noinclude>.*?<\\/noinclude>", Pattern.DOTALL);
  private static final String PARAMETER_REPLACEMENT_REGEX = "\\{\\{\\{%s\\}\\}\\}";

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

    return parameters.keySet().stream()
        .reduce(
            selectPortionsForTransclusion(input),
            (processed, parameterValue) ->
                processed.replaceAll(
                    String.format(PARAMETER_REPLACEMENT_REGEX, parameterValue),
                    parameters.getOrDefault(parameterValue, "")));
  }

  public static String selectPortionsForTransclusion(String input) {
    return NO_INCLUDE_REGEX.matcher(input).replaceAll("");
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
    for (int i = 0; i < positionalParameters.size(); i++) {
      evaluatedParameters.put(String.valueOf(i + 1), positionalParameters.get(i));
    }
    return evaluatedParameters;
  }
}
