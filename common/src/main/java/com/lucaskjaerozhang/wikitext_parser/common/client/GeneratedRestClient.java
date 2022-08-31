package com.lucaskjaerozhang.wikitext_parser.common.client;

import com.lucaskjaerozhang.wikitext_parser.common.client.responses.WikiPage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GeneratedRestClient {
  @Headers(
      "user-agent: <wikitext-parser>/<1.0> (<wikitextparser@lucaskjaerozhang.com>) <wikitext-parser/<1.0>")
  @GET("v1/page/{page}")
  Call<WikiPage> getPageSource(@Path("page") String pageTitle);
}
