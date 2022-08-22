package com.lucaskjaerozhang.wikitext_parser.ast.format;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/**
 * Bolded text.<br>
 * WikiText: '''text'''<br>
 * XML: bold
 */
public class Bold extends WikiTextParentNode implements WikiTextElement {
  /**
   * Constructs the Bold node.
   *
   * @param content The child nodes.
   */
  public Bold(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitBold(this);
  }
}
