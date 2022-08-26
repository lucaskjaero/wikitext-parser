package com.lucaskjaerozhang.wikitext_parser.ast.base;

import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** The interface for all types of AST nodes. */
public abstract class WikiTextNode implements WikiTextElement {
  /**
   * Hook for an AST visitor.
   *
   * @param <T> Whatever the visitor produces.
   * @param visitor The visitor doing the visitor.
   * @return Whatever the visitor produced.
   */
  public abstract <T> Optional<T> accept(WikiTextASTVisitor<T> visitor);

  /**
   * Base case for getting element attributes. Most nodes have no attributes
   *
   * @return Attributes
   */
  public List<NodeAttribute> getAttributes() {
    return List.of();
  }

  @Override
  public Set<String> getTemplates() {
    return Set.of();
  }

  public WikiTextNode rebuildWithContext(TreeConstructionContext context) {
    return this;
  }
}
