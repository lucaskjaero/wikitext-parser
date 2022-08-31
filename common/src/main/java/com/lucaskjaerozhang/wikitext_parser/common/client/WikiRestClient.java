package com.lucaskjaerozhang.wikitext_parser.common.client;

import java.io.IOException;
import java.util.Optional;
import lombok.Builder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Builder
public class WikiRestClient {
  @Builder.Default private final String wiki = "wikipedia";
  @Builder.Default private final String language = "en";
  private GeneratedRestClient client;

  private GeneratedRestClient getClient() {
    if (client == null) {
      Retrofit retrofit =
          new Retrofit.Builder()
              .baseUrl(String.format("https://%s.%s.org/w/rest.php/", language, wiki))
              .addConverterFactory(GsonConverterFactory.create())
              .build();

      client = retrofit.create(GeneratedRestClient.class);
    }
    return client;
  }

  public Optional<WikiPage> getPageSource(String pageTitle) {
    try {
      Response<WikiPage> response = getClient().getPageSource(pageTitle).execute();
      if (response.isSuccessful()) {
        return Optional.ofNullable(response.body());
      } else {
        return Optional.empty();
      }
    } catch (IOException e) {
      System.err.printf(
          "Failed to fetch source for page %s due to error %s", pageTitle, e.getMessage());
      return Optional.empty();
    }
  }
}
