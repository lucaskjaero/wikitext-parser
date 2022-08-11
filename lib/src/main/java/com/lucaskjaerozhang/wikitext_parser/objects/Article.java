package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public class Article implements WikiTextNode {
  private List<WikiTextNode> content;

  public Article(List<WikiTextNode> content) {
    this.content = content;
  }

  @Override
  public String getType() {
    return "Article";
  }
}
