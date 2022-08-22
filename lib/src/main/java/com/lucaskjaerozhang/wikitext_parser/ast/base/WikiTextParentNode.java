package com.lucaskjaerozhang.wikitext_parser.ast.base;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A node that has child nodes. This abstract class contains the logic for serializing the node into
 * XML.
 */
public abstract class WikiTextParentNode extends WikiTextNode {
  private final List<WikiTextNode> children;

  /**
   * Constructs the node
   *
   * @param children The child nodes
   */
  protected WikiTextParentNode(List<WikiTextNode> children) {
    this.children = children;
  }

  /**
   * Gets the child nodes of this tree node.
   *
   * @return All children in parse order.
   */
  public List<WikiTextNode> getChildren() {
    return children;
  }

  @Override
  public Set<String> getCategories() {
    return getFieldValuesFromChildren(children, WikiTextElement::getCategories);
  }

  @Override
  public Set<String> getTemplates() {
    return getFieldValuesFromChildren(children, WikiTextElement::getTemplates);
  }

  /**
   * There is data that flow up to the root from leaf nodes. In this case we can think of it as the
   * union of the child node data.
   *
   * @param children The child elements to get data from.
   * @param getter The function that gets the data
   * @return All data from the child elements.
   */
  public static Set<String> getFieldValuesFromChildren(
      List<WikiTextNode> children, Function<? super WikiTextNode, Set<String>> getter) {
    return children.stream().map(getter).flatMap(Collection::stream).collect(Collectors.toSet());
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    getAttributes().forEach(a -> a.passProps(context));
    this.children.forEach(a -> a.passProps(context));
  }
}
