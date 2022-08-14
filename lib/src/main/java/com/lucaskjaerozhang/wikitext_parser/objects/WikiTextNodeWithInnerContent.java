package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

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

  protected String makeAttributesString() {
    List<NodeAttribute> attributes = getAttributes();
    if (attributes.isEmpty()) return "";
    return attributes.stream()
        // Intentionally sorting this so attributes don't randomly change order
        .sorted()
        .map(NodeAttribute::toXML)
        .reduce("", (a, b) -> a.concat(" ").concat(b));
  }

  @Override
  public String toXML() {
    String tag = getXMLTag();
    String attributes = makeAttributesString();
    String content = getStringValue(this.content);

    return String.format("<%s%s>%s</%s>", tag, attributes, content, tag);
  }

  public List<WikiTextNode> getContent() {
    return content;
  }
}
