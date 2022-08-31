package com.lucaskjaerozhang.wikitext_parser.common.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.lucaskjaerozhang.wikitext_parser.common.client.responses.WikiPage;
import java.io.IOException;
import java.util.Optional;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

class WikiRestClientTest {
  private final GeneratedRestClient mockClient = mock(GeneratedRestClient.class);
  private final WikiRestClient testClient = WikiRestClient.builder().client(mockClient).build();

  @Test
  void canBuildWithoutErrors() {
    Assertions.assertDoesNotThrow(
        () -> {
          WikiRestClient.builder().build();
        });
    Assertions.assertDoesNotThrow(
        () -> {
          WikiRestClient.builder().wiki("wikipedia").build();
        });
    Assertions.assertDoesNotThrow(
        () -> {
          WikiRestClient.builder().wiki("wikipedia").language("en").build();
        });
  }

  @Test
  void canGetTemplateText() throws IOException {
    Call<WikiPage> testCall = mock(Call.class);
    when(mockClient.getPageSource("Jupiter")).thenReturn(testCall);

    WikiPage testResult = new WikiPage();
    testResult.setSource("testSource");
    Response<WikiPage> testResponse = Response.success(testResult);
    when(testCall.execute()).thenReturn(testResponse);

    Optional<WikiPage> template = testClient.getPageSource("Jupiter");
    Assertions.assertTrue(template.isPresent());

    String jupiter = template.get().getSource();
    Assertions.assertEquals(testResult.getSource(), jupiter);
  }

  @Test
  void returnsEmptyOptionalWhenCallFails() throws IOException {
    Call<WikiPage> testCall = mock(Call.class);
    when(mockClient.getPageSource("Jupiter")).thenReturn(testCall);

    Response<WikiPage> testResponse =
        Response.error(404, ResponseBody.create(MediaType.get("text/plain"), "TemplateNotFound"));
    when(testCall.execute()).thenReturn(testResponse);

    Optional<WikiPage> template = testClient.getPageSource("Jupiter");
    Assertions.assertTrue(template.isEmpty());
  }

  @Test
  void returnsEmptyOptionalWhenCallThrowsException() throws IOException {
    Call<WikiPage> testCall = mock(Call.class);
    when(mockClient.getPageSource("Jupiter")).thenReturn(testCall);
    when(testCall.execute()).thenThrow(new IOException("Oh no"));

    Optional<WikiPage> template = testClient.getPageSource("Jupiter");
    Assertions.assertTrue(template.isEmpty());
  }
}
