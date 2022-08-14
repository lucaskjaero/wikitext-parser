package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public interface WikiTextNode {
  String getXMLTag();

  String toXML();

  // This could be forced but honestly most node types don't have it.
  default List<NodeAttribute> getAttributes() {
    return List.of();
  }
}
