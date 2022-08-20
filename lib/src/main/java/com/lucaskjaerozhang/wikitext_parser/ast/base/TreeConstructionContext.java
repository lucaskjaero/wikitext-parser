package com.lucaskjaerozhang.wikitext_parser.ast.base;

/**
 * Context that is passed through the tree as it is constructed. This is used to enable parent nodes
 * to control the behavior of their children. For more information, see WikiTextElement.passProps().
 *
 * @param plainLinks Whether links should have an arrow.
 */
public record TreeConstructionContext(Boolean plainLinks) {
  /**
   * Generates the context with default values
   *
   * @return A context with default values.
   */
  public static TreeConstructionContext defaultContext() {
    return new TreeConstructionContext(false);
  }

  /**
   * Allows changing the withPlainLinks attribute of the context.
   *
   * @param newValue The new withPlainLinks value.
   * @return A copy of the context with the new value set.
   */
  public TreeConstructionContext withPlainLinks(Boolean newValue) {
    return new TreeConstructionContext(newValue);
  }
}
