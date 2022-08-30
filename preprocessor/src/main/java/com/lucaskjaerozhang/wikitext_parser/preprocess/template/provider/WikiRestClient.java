package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WikiRestClient {
  @Headers(
      "user-agent: <wikitext-parser>/<1.0> (<wikitextparser@lucaskjaerozhang.com>) <wikitext-parser/<1.0>")
  @GET("w/rest.php/v1/page/{page}")
  Call<WikiSourceResponse> getPageSource(@Path("page") String pageTitle);
}
