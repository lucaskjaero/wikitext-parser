package com.lucaskjaerozhang.wikitext_parser.ast.sections;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;

/**
 * A horizontal rule.<br>
 * WikiText: ----<br>
 * XML: horizontalRule
 */
public class HorizontalRule extends WikiTextLeafNode {
  public static final String XML_TAG = "horizontalRule";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
