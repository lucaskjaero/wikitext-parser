package com.lucaskjaerozhang.wikitext_parser.ast.base;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A node that has child nodes. This abstract class contains the logic for serializing the node into
 * XML.
 */
public abstract class WikiTextParentNode extends WikiTextNode {
  private final List<WikiTextNode> children;

  protected WikiTextParentNode(List<WikiTextNode> children) {
    this.children = children;
  }

  public List<WikiTextNode> getChildren() {
    return children;
  }

  /**
   * Helper method to deal with lists of nodes because this is the most common scenario.
   *
   * @param content A list of nodes.
   * @return The xml representation of that list.
   */
  protected String getStringValue(List<WikiTextNode> content) {
    return content.stream().map(WikiTextNode::toXML).reduce("", String::concat);
  }

  @Override
  public String toXML() {
    String tag = getXMLTag();

    List<NodeAttribute> attributes = getAttributes();
    if (attributes.isEmpty()) {
      return String.format("<%s>%s</%s>", tag, getStringValue(this.children), tag);
    }

    String attributesString = NodeAttribute.makeAttributesString(attributes);
    return String.format(
        "<%s %s>%s</%s>", tag, attributesString, getStringValue(this.children), tag);
  }

  @Override
  public Set<String> getCategories() {
    return getCategoriesFromChildren(children);
  }

  /**
   * Categories are links that flow up to the root from leaf nodes. In this case we can think of it
   * as the union of the child node categories.
   *
   * @param children The child elements to get categories from.
   * @return All categories from the child elements.
   */
  public static Set<String> getCategoriesFromChildren(List<WikiTextNode> children) {
    return children.stream()
        .map(WikiTextNode::getCategories)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    getAttributes().forEach(a -> a.passProps(context));
    this.children.forEach(a -> a.passProps(context));
  }
}
