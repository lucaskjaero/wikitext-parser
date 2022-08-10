package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public record Section(
    String title, Integer level, List<WikiTextNode> content, List<Section> subsections)
    implements WikiTextNode {
  public static final String TYPE = "Section";

  @Override
  public String getType() {
    return TYPE;
  }
}
