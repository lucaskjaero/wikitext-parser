package com.lucaskjaerozhang.wikitext_parser.common.client.responses;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class WikiPage {
  private Integer id;
  private String key;
  private String title;
  private LatestRevision latest;

  @SerializedName("content_model")
  private String contentModel;

  private License license;

  @SerializedName("html_url")
  private String htmlUrl;

  private String source;

  @Getter
  @Setter
  public static class LatestRevision {
    private int id;
    private String timestamp;
  }

  @Getter
  @Setter
  public static class License {
    private String url;
    private String title;
  }
}
