package com.lucaskjaerozhang.wikitext_parser.ast.base;

import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/** The interface for all types of AST nodes. */
public abstract class WikiTextNode implements WikiTextElement {
  /**
   * Gets the tag that this node will use when being serialized into XML
   *
   * @return The XML tag.
   */
  public abstract String getXMLTag();

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
  protected List<NodeAttribute> getAttributes() {
    return List.of();
  }
}
