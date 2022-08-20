package com.lucaskjaerozhang.wikitext_parser.ast.base;

import java.util.List;

/** The interface for all types of AST nodes. */
public abstract class WikiTextNode implements WikiTextElement {
  /**
   * Gets the tag that this node will use when being serialized into XML
   *
   * @return The XML tag.
   */
  public abstract String getXMLTag();

  /**
   * Base case for getting element attributes. Most nodes have no attributes
   *
   * @return Attributes
   */
  protected List<NodeAttribute> getAttributes() {
    return List.of();
  }
}
