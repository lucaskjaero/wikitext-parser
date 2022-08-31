package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RESTTemplateProvider implements TemplateProvider {
  private final WikiRestClient client;

  @Override
  public Optional<String> getTemplate(String template) {
    return client.getPageSource(template).flatMap(page -> Optional.ofNullable(page.getSource()));
  }
}
