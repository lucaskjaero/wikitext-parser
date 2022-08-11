package com.lucaskjaerozhang.wikitext_parser.objects;

public record TextNode(String contents) implements WikiTextNode {
  @Override
  public String getType() {
    return "Text";
  }
}
