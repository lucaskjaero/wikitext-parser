package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

public class Text implements WikiTextNode {
  private final String content;

  public Text(String contents) {
    this.content = contents;
  }

  @Override
  public String getType() {
    return "Text";
  }

  @Override
  public String toString() {
    return content;
  }
}
