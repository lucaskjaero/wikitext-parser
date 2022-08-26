package com.lucaskjaerozhang.wikitext_parser.ast.base;

import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Optional;
import lombok.Getter;

/** An attribute for a node. In xml is serialized as key='value' */
@Getter
public class NodeAttribute implements WikiTextElement, Comparable<NodeAttribute> {
  private final String key;
  private final String value;
  private final boolean doubleQuotes;

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
    this.doubleQuotes = usesDoubleQuotes;
  }

  /**
   * An attribute for a node. The constructor will trim spaces from the key and value.
   *
   * @param key The attribute key.
   * @param value The attribute value.
   */
  public NodeAttribute(String key, String value) {
    this.key = key.trim();
    this.value = value.trim();
    this.doubleQuotes = false;
  }

  @Override
  public int compareTo(NodeAttribute o) {
    return key.compareTo(o.key);
  }

  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitNodeAttribute(this);
  }
}
