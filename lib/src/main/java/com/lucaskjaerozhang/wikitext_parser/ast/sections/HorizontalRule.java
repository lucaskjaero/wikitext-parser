package com.lucaskjaerozhang.wikitext_parser.ast.sections;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;

public class HorizontalRule extends WikiTextLeafNode {
  public static final String XML_TAG = "horizontalRule";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
