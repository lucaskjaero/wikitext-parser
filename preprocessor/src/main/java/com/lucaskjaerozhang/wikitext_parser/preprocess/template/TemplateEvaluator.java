package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEvaluator {
  private static final Pattern NO_INCLUDE_REGEX =
      Pattern.compile("<noinclude>.*?</noinclude>", Pattern.DOTALL);
  private static final Pattern ONLY_INCLUDE_REGEX =
      Pattern.compile(".*?<includeonly>(.*?)</includeonly>.*", Pattern.DOTALL);
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
                selectPortionsForTransclusion(input),
                (processed, parameterValue) ->
                    processed.replaceAll(
                        String.format(PARAMETER_REPLACEMENT_REGEX, parameterValue),
                        parameters.getOrDefault(parameterValue, "")));

    return PARAMETER_WITH_DEFAULT_REGEX
        .matcher(providedParametersReplaced)
        .replaceAll(result -> result.group(2));
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
