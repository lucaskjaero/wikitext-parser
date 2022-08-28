package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateProcessor {
  private static final Pattern NAMED_PARAMETER_REGEX = Pattern.compile("([^=]+)=(.*)");

  private static final TemplateParameterSubstituter substituter =
      new TemplateParameterSubstituter();
  private final TemplateHolder templateHolder = TemplateHolder.getTemplateHolder();

  public String processTemplate(String templateName, TemplateProvider provider) {
    return templateHolder.getTemplate(templateName, provider);
  }

  public String processTemplate(
      String templateName, TemplateProvider provider, List<String> parameters) {
    String template = templateHolder.getTemplate(templateName, provider);

    Map<Boolean, List<String>> filteredParams =
        parameters.stream()
            .collect(Collectors.partitioningBy(p -> NAMED_PARAMETER_REGEX.asPredicate().test(p)));

    Map<String, String> namedParameters =
        filteredParams.get(true).stream()
            .map(param -> NAMED_PARAMETER_REGEX.matcher(param).results().toList().get(0))
            .collect(Collectors.toMap(r -> r.group(1), r -> r.group(2)));

    List<String> positionalParameters = filteredParams.get(false);

    return substituter.evaluateTemplate(template, positionalParameters, namedParameters);
  }
}
