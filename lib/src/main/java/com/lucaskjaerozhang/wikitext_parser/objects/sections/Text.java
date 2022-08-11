package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

public record Text(String contents) implements WikiTextNode {
  @Override
  public String getType() {
    return "Text";
  }
}
