package com.lucaskjaerozhang.wikitext_parser.ast.base;

import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Optional;

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
  public int compareTo(NodeAttribute o) {
    return key.compareTo(o.key);
  }

  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitNodeAttribute(this);
  }
}
