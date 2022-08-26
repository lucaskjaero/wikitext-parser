package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/**
 * A named template parameter.<br>
 * WikiText: {{template|key=value}}<br>
 * XML: parameter
 */
public class NamedTemplateParameter extends WikiTextNode {
  private final String key;
  private final String value;

  /**
   * Creates a new template parameter
   *
   * @param key The parameter name
   * @param value The parameter value
   */
  public NamedTemplateParameter(String key, String value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("key", key), new NodeAttribute("value", value));
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitNamedTemplateParameter(this);
  }
}
