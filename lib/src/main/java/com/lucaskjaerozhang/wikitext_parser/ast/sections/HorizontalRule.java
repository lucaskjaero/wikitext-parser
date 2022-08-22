package com.lucaskjaerozhang.wikitext_parser.ast.sections;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Optional;

/**
 * A horizontal rule.<br>
 * WikiText: ----<br>
 * XML: horizontalRule
 */
public class HorizontalRule extends WikiTextNode {
  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitHorizontalRule(this);
  }
}
