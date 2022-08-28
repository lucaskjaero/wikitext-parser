package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.List;

public interface TemplateProvider {
  String getTemplate(String template);

  List<String> getTemplates(List<String> templates);
}
