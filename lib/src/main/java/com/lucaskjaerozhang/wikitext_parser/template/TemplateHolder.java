package com.lucaskjaerozhang.wikitext_parser.template;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TemplateHolder {
  private static final String CHARACTER_REFERENCE = "(?>&[A-Za-z0-9#]+;)";
  private static final String TEMPLATE_NAME =
      String.format("([\\p{Alnum},.?，。《》？() \\-%s]+)", CHARACTER_REFERENCE);
  private static final String TEMPLATE_PARAMETERS = "((?>\\|[^}^\\|]+)*)";
  // After compilation this pattern becomes '\{\{([\p{Alnum},.?，。《》？()
  // \-(?>&[A-Za-z0-9#]+;)]+)((?>\|[^}^\|]+)*)}}'
  private static final Pattern TEMPLATE_INVOCATION_REGEX =
      Pattern.compile(String.format("\\{\\{%s%s}}", TEMPLATE_NAME, TEMPLATE_PARAMETERS));

  private final Map<String, String> templates;

  public TemplateHolder(Map<String, String> templates) {
    this.templates = templates;
    checkTemplateDependencies();
  }

  private void checkTemplateDependencies() {
    Map<String, List<String>> templateDependencies =
        templates.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, e -> calculateTemplateDependencies(e.getValue())));

    templateDependencies.forEach(
        (key, value) -> {
          if (!templates.keySet().containsAll(value)) {
            throw new IllegalArgumentException(
                String.format(
                    "Missing dependent templates for template %s, required templates: %s",
                    key, String.join(", ", value)));
          }
        });
  }

  private List<String> calculateTemplateDependencies(String template) {
    return TEMPLATE_INVOCATION_REGEX.matcher(template).results().map(m -> m.group(1)).toList();
  }
}
