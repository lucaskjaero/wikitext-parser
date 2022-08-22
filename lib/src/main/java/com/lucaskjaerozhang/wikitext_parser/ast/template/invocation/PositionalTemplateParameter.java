package com.lucaskjaerozhang.wikitext_parser.ast.template.invocation;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/**
 * An unnamed template parameter referred to by position. Eg. the first one is 1, the second is 2,
 * etc.<br>
 * WikiText: {{template|parameter}} XML: parameter
 */
public class PositionalTemplateParameter extends WikiTextNode {
  private final String value;

  /**
   * Creates a positional template parameter
   *
   * @param value The parameter value
   */
  public PositionalTemplateParameter(String value) {
    this.value = value;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("value", value, false));
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitPositionalTemplateParameter(this);
  }
}
