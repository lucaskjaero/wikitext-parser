package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import static org.mockito.Mockito.when;

import com.lucaskjaerozhang.wikitext_parser.common.client.WikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.responses.WikiPage;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OnlineTemplateProviderTest {
  private final WikiClient mockClient = Mockito.mock(WikiRestClient.class);
  private final OnlineTemplateProvider testProvider = new OnlineTemplateProvider(mockClient);

  @Test
  void canGetTemplateText() {
    WikiPage testResult = new WikiPage();
    testResult.setSource("testSource");
    when(mockClient.getPageSource("Jupiter")).thenReturn(Optional.of(testResult));

    Optional<String> template = testProvider.getTemplate("Jupiter");
    Assertions.assertTrue(template.isPresent());

    String jupiter = template.get();
    Assertions.assertEquals(testResult.getSource(), jupiter);
  }

  @Test
  void returnsEmptyOptionalWhenCallFails() {
    when(mockClient.getPageSource("Jupiter")).thenReturn(Optional.empty());

    Optional<String> template = testProvider.getTemplate("Jupiter");
    Assertions.assertTrue(template.isEmpty());
  }
}
