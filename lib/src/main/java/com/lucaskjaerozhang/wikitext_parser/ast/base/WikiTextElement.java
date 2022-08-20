package com.lucaskjaerozhang.wikitext_parser.ast.base;

import java.util.Set;

/**
 * The main interface for all AST nodes. Tree is constructed in these steps:
 *
 * <ol>
 *   <li>Construct the AST nodes from the parse tree by walking the parse tree in a depth-first
 *       search and building the AST from the bottom up.
 *   <li>Pass context from the root down, mutating elements as needed.
 *   <li>Render the tree from the bottom up, passing context back up as needed. (eg. page
 *       categories)
 * </ol>
 *
 * <p>Context passing should happen only once in each direction to avoid complexity. Passing context
 * downwards should be done as little as possible.
 *
 * <p>All nodes should extend from `WikiTextParentNode` or `WikiTextLeafNode` as they do most of the
 * passing and rendering heavy lifting for you.
 */
public interface WikiTextElement {
  /**
   * Serializes the tree as XML.
   *
   * @return The tree as XML.
   */
  String toXML();

  /**
   * Some nodes impact how their children should be processed. This gives a mechanism for passing
   * that context down. If you've used react, this works a lot like props.
   *
   * <p>This needs to happen during the construction of the tree.
   *
   * @param context The context to pass.
   */
  void passProps(TreeConstructionContext context);

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
