package com.lucaskjaerozhang.wikitext_parser.ast.sections;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Optional;

/**
 * A horizontal rule.<br>
 * WikiText: ----<br>
 * XML: horizontalRule
 */
public class HorizontalRule extends WikiTextLeafNode {
  private static final String XML_TAG = "horizontalRule";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitHorizontalRule(this);
  }
}
