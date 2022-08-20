package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

/**
 * An XML node with no children. In WikiText, you can pass through HTML or special xml tags. This
 * handles them as a group. Examples: - blockquote - poem
 */
public class XMLStandaloneElement extends WikiTextLeafNode {
  private final String tag;
  private final List<NodeAttribute> attributes;

  /**
   * Creates an XML node with no children.
   *
   * @param tag The XML tag.
   * @param attributes Any xml attributes.
   */
  public XMLStandaloneElement(String tag, List<NodeAttribute> attributes) {
    this.tag = tag.trim();
    this.attributes = attributes;
  }

  @Override
  public String getXMLTag() {
    return tag;
  }

  @Override
  public String toXML() {
    return String.format("<%s %s/>", tag, NodeAttribute.makeAttributesString(attributes));
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return attributes;
  }
}
