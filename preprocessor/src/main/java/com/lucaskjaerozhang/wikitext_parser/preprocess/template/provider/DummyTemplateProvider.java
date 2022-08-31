package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.Optional;

public class DummyTemplateProvider implements TemplateProvider {
  @Override
  public Optional<String> getTemplate(String template) {
    return Optional.of("");
  }
}
