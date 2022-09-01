package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import com.lucaskjaerozhang.wikitext_parser.common.client.WikiClient;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnlineTemplateProvider implements TemplateProvider {
  private final WikiClient client;

  @Override
  public Optional<String> getTemplate(String template) {
    String templatePath = String.format("Template:%s", template);
    return client
        .getPageSource(templatePath)
        .or(() -> client.getPageSource(template))
        .flatMap(page -> Optional.ofNullable(page.getSource()));
  }
}
