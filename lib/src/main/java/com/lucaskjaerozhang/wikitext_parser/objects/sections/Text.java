package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;

public record Text(String content) implements WikiTextNode {
  public static final String XML_TAG = "text";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public String toXML() {
    return content;
  }
}
