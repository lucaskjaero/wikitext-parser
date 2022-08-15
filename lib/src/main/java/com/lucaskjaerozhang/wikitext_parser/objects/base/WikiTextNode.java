package com.lucaskjaerozhang.wikitext_parser.objects.base;

import java.util.List;

public abstract class WikiTextNode implements WikiTextElement {
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
