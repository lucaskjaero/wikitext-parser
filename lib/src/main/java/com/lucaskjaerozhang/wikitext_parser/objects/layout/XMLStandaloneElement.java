package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;

import java.util.List;

/**
 * In WikiText, you can pass through HTML or special xml tags. This handles them as a group.
 * Examples: - blockquote - poem
 */
public record XMLStandaloneElement(String tag, List<NodeAttribute> attributes) implements WikiTextNode {

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
