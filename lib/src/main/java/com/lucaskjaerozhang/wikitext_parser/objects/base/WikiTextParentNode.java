package com.lucaskjaerozhang.wikitext_parser.objects.base;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class WikiTextParentNode extends WikiTextNode {
  private final List<WikiTextNode> children;

  public List<WikiTextNode> getChildren() {
    return children;
  }

  protected WikiTextParentNode(List<WikiTextNode> content) {
    this.children = content;
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
   * @return All categories from the child elements.
   */
  public static Set<String> getCategoriesFromChildren(List<WikiTextNode> children) {
    return children.stream()
        .map(WikiTextNode::getCategories)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }
}
