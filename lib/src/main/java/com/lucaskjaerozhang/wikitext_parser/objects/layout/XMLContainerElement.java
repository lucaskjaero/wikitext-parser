package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextParentNode;
import java.util.List;

/**
 * In WikiText, you can pass through HTML or special xml tags. This handles them as a group.
 * Examples: - blockquote - poem
 */
public class XMLContainerElement extends WikiTextParentNode implements WikiTextNode {
  public final String xmlTag;
  private final List<NodeAttribute> attributes;

  public XMLContainerElement(
      String tag, List<NodeAttribute> attributes, List<WikiTextNode> content) {
    super(content);
    this.xmlTag = tag.trim();
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
