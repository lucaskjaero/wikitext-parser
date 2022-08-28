package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Getter;

@Getter
public class RawTemplateHolder {
  private final Map<String, String> templates;
  private final TemplateInvocationParser parser = new TemplateInvocationParser();

  public RawTemplateHolder(Map<String, String> templates) {
    this.templates = templates;
    checkTemplateDependencies();
  }

  /**
   * Makes sure we can actually resolve all the templates provided, given that templates can use
   * other templates. In particular, it checks for:
   *
   * <ul>
   *   <li>Dependent templates that weren't provided.
   *   <li>Cycles in the template dependency graph. Eg. template a depends on template b, which
   *       depends on template a.
   * </ul>
   */
  private void checkTemplateDependencies() {
    Set<String> visitedTemplates = new HashSet<>();

    final Queue<TemplateDependency> toVisit =
        new ConcurrentLinkedQueue<>(
            templates.entrySet().stream()
                .map(
                    e ->
                        new TemplateDependency(
                            e.getKey(), parser.calculateTemplateDependencies(e.getValue())))
                .toList());
    toVisit.forEach(
        d -> {
          String name = d.templateName();
          List<String> dependencies = d.dependencies();

          if (visitedTemplates.contains(name)) {
            throw new IllegalArgumentException(
                String.format(
                    "Template %s depends on a template that depends on %s, it's impossible to resolve this template.",
                    name, name));
          }
          visitedTemplates.add(name);

          // Then check for unsatisfied dependencies.
          if (!templates.keySet().containsAll(d.dependencies())) {
            throw new IllegalArgumentException(
                String.format(
                    "Missing dependent templates for template %s, required templates: %s",
                    name, String.join(", ", dependencies)));
          }
        });
  }

  public Optional<String> getTemplate(String templateName) {
    return templates.containsKey(templateName)
        ? Optional.of(templates.get(templateName))
        : Optional.empty();
  }

  private record TemplateDependency(String templateName, List<String> dependencies) {}
}
