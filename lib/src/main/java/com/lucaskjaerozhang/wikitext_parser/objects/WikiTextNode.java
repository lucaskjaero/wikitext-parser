package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.Map;

public interface WikiTextNode {
  String getXMLTag();

  String toXML();

  // This could be forced but honestly most node types don't have it.
  default Map<String, String> getAttributes() {
    return Map.of();
  }
}
