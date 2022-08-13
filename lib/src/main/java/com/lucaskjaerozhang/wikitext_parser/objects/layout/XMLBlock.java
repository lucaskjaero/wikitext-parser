package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;
import java.util.List;
import java.util.Map;

/**
 * In WikiText, you can pass through HTML or special xml tags. This handles them as a group.
 * Examples: - blockquote - poem
 */
public class XMLBlock extends WikiTextNodeWithInnerContent implements WikiTextNode {
  public final String xmlTag;
  private final Map<String, String> attributes;

  public XMLBlock(String tag, Map<String, String> attributes, List<WikiTextNode> content) {
    super(content);
    this.xmlTag = tag;
    this.attributes = attributes;
  }

  @Override
  public String getXMLTag() {
    return xmlTag;
  }

  @Override
  public Map<String, String> getAttributes() {
    return attributes;
  }
}
