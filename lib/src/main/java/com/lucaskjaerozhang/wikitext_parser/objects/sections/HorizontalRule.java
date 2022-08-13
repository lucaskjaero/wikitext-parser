package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

public class HorizontalRule implements WikiTextNode {
  public static final String XML_TAG = "horizontalRule";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public String toXML() {
    return String.format("<%s />", XML_TAG);
  }
}
