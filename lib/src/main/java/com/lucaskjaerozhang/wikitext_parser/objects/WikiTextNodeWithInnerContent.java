package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;
import java.util.Map;

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
    Map<String, String> attributesMap = getAttributes();
    if (attributesMap.isEmpty()) return "";
    return attributesMap.keySet().stream()
        // Intentionally sorting this so attributes don't randomly change order
        .sorted()
        .map(e -> String.format("%s='%s'", e, attributesMap.get(e)))
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
