package com.lucaskjaerozhang.wikitext_parser.parse.intermediatestate;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

public record TagAttribute(String key, String value) implements WikiTextNode {
  @Override
  public String getXMLTag() {
    return null;
  }

  @Override
  public String toXML() {
    throw new UnsupportedOperationException("Attempting to serialize an intermediate type");
  }
}
