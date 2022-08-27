package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateEvaluator {
  private static final Pattern NO_INCLUDE_REGEX =
      Pattern.compile("<noinclude>.*?</noinclude>", Pattern.DOTALL);
  private static final Pattern ONLY_INCLUDE_REGEX =
      Pattern.compile(".*?<includeonly>(.*?)</includeonly>.*", Pattern.DOTALL);
  private static final Pattern PARAMETER_REGEX = Pattern.compile("\\{\\{\\{([^}]+)}}}");
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
    Map<String, String> parameters =
        evaluateParameterValues(input, positionalParameters, namedParameters);

    return parameters.keySet().stream()
        .reduce(
            selectPortionsForTransclusion(input),
            (processed, parameterValue) ->
                processed.replaceAll(
                    String.format(PARAMETER_REPLACEMENT_REGEX, parameterValue),
                    parameters.getOrDefault(parameterValue, "")));
  }

  /**
   * Removes noinclude blocks, and then checks for onlyinclude blocks.
   *
   * @param input The full template input.
   * @return The portion of the template that will be transcluded.
   */
  private static String selectPortionsForTransclusion(String input) {
    String noIncludeRemoved = NO_INCLUDE_REGEX.matcher(input).replaceAll("");
    Matcher matcher = ONLY_INCLUDE_REGEX.matcher(input);
    return matcher.matches() ? matcher.group(1) : noIncludeRemoved;
  }

  /**
   * Combines the named and positional parameters into one lookup table for substitution.
   *
   * @param input The input template. Needed to put default values in optional parameters.
   * @param positionalParameters Parameters that are specified by position.
   * @param namedParameters Parameters that are specified by name.
   * @return The lookup table.
   */
  private static Map<String, String> evaluateParameterValues(
      String input, List<String> positionalParameters, Map<String, String> namedParameters) {
    Map<String, String> evaluatedParameters = new HashMap<>(namedParameters);

    // Translate positional parameters to named ones for the table.
    for (int i = 0; i < positionalParameters.size(); i++) {
      evaluatedParameters.put(String.valueOf(i + 1), positionalParameters.get(i));
    }

    // Parameters can have default valuess.
    getParametersInTemplate(input).stream()
        .filter(p -> p.contains("|"))
        .forEach(
            parameter -> {
              String[] values = parameter.split("\\|");
              String key = values[0];
              if (!evaluatedParameters.containsKey(key)) {
                String defaultValue = values.length > 1 ? values[1] : "";
                evaluatedParameters.put(key, defaultValue);
              }
            });

    return evaluatedParameters;
  }

  private static Set<String> getParametersInTemplate(String inputText) {
    return PARAMETER_REGEX
        .matcher(inputText)
        .results()
        .map(r -> r.group(1))
        .collect(Collectors.toUnmodifiableSet());
  }
}
