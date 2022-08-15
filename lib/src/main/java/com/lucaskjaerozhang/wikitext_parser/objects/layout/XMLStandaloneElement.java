package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import java.util.List;

/**
 * In WikiText, you can pass through HTML or special xml tags. This handles them as a group.
 * Examples: - blockquote - poem
 */
public record XMLStandaloneElement(String tag, List<NodeAttribute> attributes)
    implements WikiTextNode {

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
