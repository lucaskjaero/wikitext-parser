package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.io.IOException;
import java.util.Optional;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTTemplateProvider implements TemplateProvider {
  private final WikiRestClient client;

  public RESTTemplateProvider(String baseUrl) {
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    client = retrofit.create(WikiRestClient.class);
  }

  @Override
  public Optional<String> getTemplate(String template) {
    try {
      Response<WikiSourceResponse> response = client.getPageSource(template).execute();
      if (response.isSuccessful()) {
        return Optional.ofNullable(response.body())
            .flatMap(resp -> Optional.ofNullable(resp.getItems()))
            .flatMap(item -> item.isEmpty() ? Optional.empty() : Optional.of(item.get(0)))
            .flatMap(page -> Optional.ofNullable(page.getSource()));
      } else {
        return Optional.empty();
      }
    } catch (IOException e) {
      System.err.printf("Failed to fetch template %s due to error %s", template, e.getMessage());
      return Optional.empty();
    }
  }
}
