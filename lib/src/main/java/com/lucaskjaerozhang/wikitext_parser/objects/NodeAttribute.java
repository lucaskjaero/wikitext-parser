package com.lucaskjaerozhang.wikitext_parser.objects;

public record NodeAttribute(String key, String value, boolean usesDoubleQuotes)
    implements WikiTextNode, Comparable<NodeAttribute> {
  @Override
  public String getXMLTag() {
    return null;
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
}
