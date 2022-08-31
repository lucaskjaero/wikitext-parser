package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RestTemplateProviderTest {
  private final RESTTemplateProvider testProvider = RESTTemplateProvider.builder().build();

  @Test
  void doesNotThrowErrors() {
    Optional<String> template = testProvider.getTemplate("Jupiter");
    Assertions.assertTrue(template.isPresent());

    String jupiter = template.get();
    Assertions.assertNotNull(jupiter);
  }
}
