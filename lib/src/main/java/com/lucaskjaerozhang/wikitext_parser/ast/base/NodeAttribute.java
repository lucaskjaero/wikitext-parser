package com.lucaskjaerozhang.wikitext_parser.ast.base;

import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An attribute for a node. In xml is serialized as key='value'
 *
 * @param key The attribute key.
 * @param value The attribute value.
 * @param usesDoubleQuotes Whether the value should be wrapped in double quotes. By default this is
 *     false, unless the source text uses them.
 */
public record NodeAttribute(String key, String value, boolean usesDoubleQuotes)
    implements WikiTextElement, Comparable<NodeAttribute> {

  /**
   * An attribute for a node. The constructor will trim spaces from the key and value.
   *
   * @param key The attribute key.
   * @param value The attribute value.
   * @param usesDoubleQuotes Whether the value should be wrapped in double quotes. By default this
   *     is false, unless the source text uses them.
   */
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
  public void passProps(TreeConstructionContext context) {
    /* No action */
  }

  @Override
  public int compareTo(NodeAttribute o) {
    return key.compareTo(o.key);
  }

  /**
   * Creates the xml string for attributes.
   *
   * @param attributes The attributes to serialize.
   * @return The attributes string.
   */
  public static String makeAttributesString(List<NodeAttribute> attributes) {
    if (attributes.isEmpty()) return "";
    return attributes.stream()
        // Intentionally sorting this so attributes don't randomly change order
        .sorted()
        .map(NodeAttribute::toXML)
        .collect(Collectors.joining(" "));
  }

  public <T> T accept(WikiTextASTVisitor<? extends T> visitor) {
    return visitor.visitNodeAttribute(this);
  }
}
