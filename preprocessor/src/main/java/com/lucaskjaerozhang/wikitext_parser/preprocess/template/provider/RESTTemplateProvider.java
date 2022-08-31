package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import com.lucaskjaerozhang.wikitext_parser.common.client.WikiPage;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.io.IOException;
import java.util.Optional;
import lombok.Builder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Builder
public class RESTTemplateProvider implements TemplateProvider {
  @Builder.Default private final String wiki = "wikipedia";
  @Builder.Default private final String language = "en";
  private WikiRestClient client;

  private WikiRestClient getClient() {
    if (client == null) {
      Retrofit retrofit =
          new Retrofit.Builder()
              .baseUrl(String.format("https://%s.%s.org/w/rest.php/", language, wiki))
              .addConverterFactory(GsonConverterFactory.create())
              .build();

      client = retrofit.create(WikiRestClient.class);
    }
    return client;
  }

  @Override
  public Optional<String> getTemplate(String template) {
    try {
      Response<WikiPage> response = getClient().getPageSource(template).execute();
      if (response.isSuccessful()) {
        return Optional.ofNullable(response.body())
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
