package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public record Article(List<WikiTextNode> content) implements WikiTextNode {
  public static final String XML_TAG = "article";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public String getContentAsString() {
    return getStringValue(content);
  }
}
