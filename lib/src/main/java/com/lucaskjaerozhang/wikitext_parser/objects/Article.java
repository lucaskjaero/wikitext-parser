package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public class Article extends WikiTextNodeWithInnerContent implements WikiTextNode {
  public static final String XML_TAG = "article";

  public Article(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
