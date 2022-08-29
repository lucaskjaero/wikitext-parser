package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

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

    String resolved = resolveTemplate(templateName, provider);
    resolvedTemplates.put(templateName, resolved);

    return resolved;
  }

  private String resolveTemplate(String templateName, TemplateProvider templateProvider) {
    return templateProvider.getTemplate(templateName);
  }
}
