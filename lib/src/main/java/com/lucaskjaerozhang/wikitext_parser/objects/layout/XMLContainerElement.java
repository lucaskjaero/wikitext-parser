package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;
import java.util.List;

/**
 * In WikiText, you can pass through HTML or special xml tags. This handles them as a group.
 * Examples: - blockquote - poem
 */
public class XMLContainerElement extends WikiTextNodeWithInnerContent implements WikiTextNode {
  public final String xmlTag;
  private final List<NodeAttribute> attributes;

  public XMLContainerElement(String tag, List<NodeAttribute> attributes, List<WikiTextNode> content) {
    super(content);
    this.xmlTag = tag;
    this.attributes = attributes;
  }

  @Override
  public String getXMLTag() {
    return xmlTag;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return attributes;
  }
}
