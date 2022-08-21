package com.lucaskjaerozhang.wikitext_parser.ast.root;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import com.lucaskjaerozhang.wikitext_parser.ast.link.WikiLink;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/** Alternative to an article indicating the article redirects to another one. */
public class Redirect extends WikiTextLeafNode implements WikiTextElement {
  private final WikiLink redirectTo;

  /**
   * Creates a redirect.
   *
   * @param redirectTo The article to redirect to.
   */
  public Redirect(WikiLink redirectTo) {
    this.redirectTo = redirectTo;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitRedirect(this);
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return redirectTo.getAttributes();
  }
}
