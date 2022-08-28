package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import com.lucaskjaerozhang.wikitext_parser.preprocess.PreprocessorVariables;
import java.util.*;

public class TemplateHolder {
  private static TemplateHolder singleton;
  private final Map<String, String> resolvedTemplates = new HashMap<>();

  public static TemplateHolder getTemplateHolder() {
    if (singleton == null) {
      singleton = new TemplateHolder();
    }
    return singleton;
  }

  public String getTemplate(String templateName, TemplateProvider provider) {
    if (resolvedTemplates.containsKey(templateName)) return resolvedTemplates.get(templateName);
    return getTemplate(templateName, provider, new ArrayList<>());
  }

  private String getTemplate(
      String templateName, TemplateProvider provider, List<String> visitedTemplates) {
    if (resolvedTemplates.containsKey(templateName)) return resolvedTemplates.get(templateName);

    if (visitedTemplates.contains(templateName)) {
      throw new IllegalArgumentException(
          String.format(
              "Template %s depends on a template that depends on %s, it's impossible to resolve this template. Resolution chain: %s",
              templateName, templateName, String.join(" -> ", visitedTemplates)));
    }
    visitedTemplates.add(templateName);

    String resolved = resolveTemplate(templateName, provider, visitedTemplates);
    resolvedTemplates.put(templateName, resolved);

    return resolved;
  }

  private String resolveTemplate(
      String templateName, TemplateProvider templateProvider, List<String> visitedTemplates) {
    String template = templateProvider.getTemplate(templateName);

    Preprocessor preprocessor =
        new Preprocessor(new PreprocessorVariables(Map.of()), visitedTemplates);
    return preprocessor.preprocess(template, true);
  }
}
