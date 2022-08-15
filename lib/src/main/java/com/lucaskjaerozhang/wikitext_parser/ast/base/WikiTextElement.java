package com.lucaskjaerozhang.wikitext_parser.ast.base;

import java.util.Set;

public interface WikiTextElement {
  String toXML();

  /**
   * Category links are leaf nodes but we want to include them as attributes of an article. This
   * gives us three cases to consider:
   *
   * <ol>
   *   <li><i>Leaf nodes that are category links</i>. We want to pass these up to the parent nodes.
   *       This is handled in Category objects.
   *   <li><i>Other leaf nodes</i>. These aren't categories and should return nothing. This is the
   *       default case in the WikiTextNode interface.
   *   <li><i>Parent nodes</i>. These aren't categories by themselves, and should return the union
   *       of the categories of their children. This is handled in WikiTextParent nodes
   * </ol>
   *
   * @return A set of categories as strings.
   */
  default Set<String> getCategories() {
    return Set.of();
  }
}
