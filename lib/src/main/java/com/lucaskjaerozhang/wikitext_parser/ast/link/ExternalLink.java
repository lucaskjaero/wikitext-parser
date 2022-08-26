package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.TreeConstructionContext;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

/**
 * A link that goes outside of the wiki.<br>
 * WikiText: [link display]<br>
 * XML: link
 */
@Getter
public class ExternalLink extends WikiTextParentNode {
  private final String href;
  private Boolean hasArrow;

  /**
   * Constructs an external link.
   *
   * @param href The url the link points to.
   * @param hasArrow Whether the link displays with an error.
   * @param display The display text.
   */
  public ExternalLink(String href, boolean hasArrow, List<WikiTextNode> display) {
    super(display);
    this.href = href;
    this.hasArrow = hasArrow;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(
        new NodeAttribute("href", this.href, false),
        new NodeAttribute("arrow", hasArrow.toString(), false));
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitExternalLink(this);
  }

  @Override
  public ExternalLink rebuildWithContext(TreeConstructionContext context) {
    super.rebuildWithContext(context);
    // reacts to being enclosed in a <span class='plainlinks'> block.
    return Boolean.TRUE.equals(context.getPlainLinks())
        ? new ExternalLink(getHref(), false, getChildren())
        : new ExternalLink(getHref(), getHasArrow(), getChildren());
  }
}
