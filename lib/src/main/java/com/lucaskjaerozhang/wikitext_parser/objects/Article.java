package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public class Article implements WikiTextNode {
  private List<Section> sections;

  public Article(List<WikiTextNode> content) {}

  public void setSections(List<Section> sections) {
    this.sections = sections;
  }

  @Override
  public String getType() {
    return "Article";
  }
}
