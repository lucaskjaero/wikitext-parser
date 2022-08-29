package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.PreprocessorVariables;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateProcessor {
  private static final Pattern NAMED_PARAMETER_REGEX = Pattern.compile("([^=]+)=(.*)");

  private static final TemplateParameterSubstituter substituter =
      new TemplateParameterSubstituter();
  private final TemplateHolder templateHolder = TemplateHolder.getTemplateHolder();

  public String processTemplate(
      String templateName, TemplateProvider provider, List<String> visitedTemplates) {
    return processTemplate(templateName, provider, visitedTemplates, List.of());
  }

  public String processTemplate(
      String templateName,
      TemplateProvider provider,
      List<String> visitedTemplates,
      List<String> parameters) {
    if (visitedTemplates.contains(templateName)) {
      throw new IllegalArgumentException(
          String.format(
              "Template %s depends on a template that depends on %s, it's impossible to resolve this template. Resolution chain: %s",
              templateName, templateName, String.join(" -> ", visitedTemplates)));
    }
    List<String> visited = new ArrayList<>(visitedTemplates);
    visited.add(templateName);

    String template = templateHolder.getTemplate(templateName, provider);
    String substituted = parameters.isEmpty() ? template : evaluateParameters(template, parameters);

    Preprocessor preprocessor =
        new Preprocessor(new PreprocessorVariables(Map.of()), provider, visited);
    return preprocessor.preprocess(substituted, true);
  }

  private String evaluateParameters(String template, List<String> parameters) {
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
