package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;
import java.util.stream.Collectors;

public abstract class WikiTextNodeWithInnerContent implements WikiTextNode {
  private final List<WikiTextNode> content;

  protected WikiTextNodeWithInnerContent(List<WikiTextNode> content) {
    this.content = content;
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
      return String.format("<%s>%s</%s>", tag, getStringValue(this.content), tag);
    }

    String attributesString = NodeAttribute.makeAttributesString(attributes);
    return String.format(
        "<%s %s>%s</%s>", tag, attributesString, getStringValue(this.content), tag);
  }

  public List<WikiTextNode> getContent() {
    return content;
  }
}
