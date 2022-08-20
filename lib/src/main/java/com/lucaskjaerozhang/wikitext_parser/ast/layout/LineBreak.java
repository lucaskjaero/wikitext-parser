package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;

/**
 * An line break<br>
 * WikiText: \n\n<br>
 * XML: br
 */
public class LineBreak extends WikiTextLeafNode {
  @Override
  public String getXMLTag() {
    return "br";
  }
}
