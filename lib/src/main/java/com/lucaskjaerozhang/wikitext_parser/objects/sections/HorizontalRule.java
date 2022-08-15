package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextLeafNode;

public class HorizontalRule extends WikiTextLeafNode {
  public static final String XML_TAG = "horizontalRule";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
