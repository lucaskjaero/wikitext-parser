package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;

public class LineBreak extends WikiTextLeafNode {
  @Override
  public String getXMLTag() {
    return "br";
  }
}
