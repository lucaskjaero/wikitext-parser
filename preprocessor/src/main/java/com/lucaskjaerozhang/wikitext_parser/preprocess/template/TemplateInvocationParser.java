package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateInvocationParser {
  private static final String CHARACTER_REFERENCE = "(?>&[A-Za-z0-9#]+;)";
  private static final String TEMPLATE_NAME =
      String.format("([\\p{Alnum},.?，。《》？() \\-%s]+)", CHARACTER_REFERENCE);
  private static final String TEMPLATE_PARAMETERS = "((?>\\|[^}^\\|]+)*)";
  // After compilation this pattern becomes '\{\{([\p{Alnum},.?，。《》？()
  // \-(?>&[A-Za-z0-9#]+;)]+)((?>\|[^}^\|]+)*)}}'
  private static final Pattern TEMPLATE_INVOCATION_REGEX =
      Pattern.compile(String.format("\\{\\{%s%s}}", TEMPLATE_NAME, TEMPLATE_PARAMETERS));
  private static final Pattern NAMED_PARAMETER_REGEX = Pattern.compile("([^=]+)=(.*)");

  public static List<String> calculateTemplateDependencies(String input) {
    return TEMPLATE_INVOCATION_REGEX.matcher(input).results().map(m -> m.group(1)).toList();
  }

  public static List<TemplateInvocation> findInvocations(String input) {
    return TEMPLATE_INVOCATION_REGEX
        .matcher(input)
        .results()
        .map(
            m -> {
              String name = m.group(1);

              Map<Boolean, List<String>> parameters =
                  Arrays.stream(m.group(2).split("\\|"))
                      .map(String::trim)
                      .collect(
                          Collectors.partitioningBy(
                              p -> NAMED_PARAMETER_REGEX.asPredicate().test(p)));

              Map<String, String> namedParameters =
                  parameters.get(true).stream()
                      .map(NAMED_PARAMETER_REGEX::matcher)
                      .collect(Collectors.toMap(p -> p.group(1), p -> p.group(2)));

              List<String> positionalParameters = parameters.get(false);

              return new TemplateInvocation(name, positionalParameters, namedParameters);
            })
        .toList();
  }
}
