package com.lucaskjaerozhang.wikitext_parser.ast.base;

/**
 * A node that does not have any children, representing the bottom of the AST. Handles
 * serialization.
 */
public abstract class WikiTextLeafNode extends WikiTextNode {
  @Override
  public void passProps(TreeConstructionContext context) {
    getAttributes().forEach(a -> a.passProps(context));
  }
}
