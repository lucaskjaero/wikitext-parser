package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public interface WikiTextNode {
  String getType();

  default String getStringValue(List<WikiTextNode> content) {
    return content.stream().map(Object::toString).reduce("", String::concat);
  }
}
