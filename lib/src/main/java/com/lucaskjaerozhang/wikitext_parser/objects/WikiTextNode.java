package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;
import java.util.Map;

public interface WikiTextNode {
  String getXMLTag();

  String getContentAsString();

  // This could be forced but honestly most node types don't have it.
  default Map<String, String> getAttributes() {
    return Map.of();
  }

  /**
   * Helper method to deal with lists of nodes because this is the most common scenario.
   * @param content A list of nodes.
   * @return The xml representation of that list.
   */
  default String getStringValue(List<WikiTextNode> content) {
    return content.stream().map(WikiTextNode::toXML).reduce("", String::concat);
  }

  default String makeAttributesString() {
    Map<String, String> attributesMap = getAttributes();
    if (attributesMap.isEmpty()) return "";
    return attributesMap.keySet().stream()
        // Intentionally sorting this so attributes don't randomly change order
        .sorted()
        .map(e -> String.format("%s='%s'", e, attributesMap.get(e)))
        .reduce("", (a, b) -> a.concat(" ").concat(b));
  }

  default String toXML() {
    String tag = getXMLTag();
    String attributes = makeAttributesString();
    String content = getContentAsString();
    return String.format("<%s%s>%s</%s>", tag, attributes, content, tag);
  }
}
