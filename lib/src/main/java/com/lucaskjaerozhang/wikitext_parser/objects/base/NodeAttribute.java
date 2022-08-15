package com.lucaskjaerozhang.wikitext_parser.objects.base;

import java.util.List;
import java.util.stream.Collectors;

public record NodeAttribute(String key, String value, boolean usesDoubleQuotes)
    implements WikiTextElement, Comparable<NodeAttribute> {
  public NodeAttribute(String key, String value, boolean usesDoubleQuotes) {
    this.key = key.trim();
    this.value = value.trim();
    this.usesDoubleQuotes = usesDoubleQuotes;
  }

  @Override
  public String toXML() {
    if (usesDoubleQuotes) return String.format("%s=\"%s\"", key, value);
    return String.format("%s='%s'", key, value);
  }

  @Override
  public int compareTo(NodeAttribute o) {
    return key.compareTo(o.key);
  }

  public static String makeAttributesString(List<NodeAttribute> attributes) {
    if (attributes.isEmpty()) return "";
    return attributes.stream()
        // Intentionally sorting this so attributes don't randomly change order
        .sorted()
        .map(NodeAttribute::toXML)
        .collect(Collectors.joining(" "));
  }
}
