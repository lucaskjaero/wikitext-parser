package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/**
 * A link that goes outside of the wiki which gets automatic numbering rather than display text.<br>
 * WikiText: [link]<br>
 * XML: link
 */
public class UnnamedExternalLink extends WikiTextNode implements WikiTextElement {
  private final String href;
  private final Boolean hasArrow;

  /**
   * Creates an external link without a name.
   *
   * @param href The link target
   * @param hasArrow Whether an arrow should be displayed on the link.
   */
  public UnnamedExternalLink(String href, Boolean hasArrow) {
    this.href = href;
    this.hasArrow = hasArrow;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(
        new NodeAttribute("href", this.href), new NodeAttribute("arrow", hasArrow.toString()));
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitUnnamedExternalLink(this);
  }
}
