package com.lucaskjaerozhang.wikitext_parser.parse.intermediatestate;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.Map;

public record OpenTag(String tag, Map<String, String> attributes) implements WikiTextNode {
  @Override
  public String getXMLTag() {
    return tag;
  }

  @Override
  public String toXML() {
    throw new UnsupportedOperationException("Attempting to serialize an intermediate type");
  }
}
