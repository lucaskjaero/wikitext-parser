package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

/**
 * An unnamed template parameter referred to by position. Eg. the first one is 1, the second is 2,
 * etc.<br>
 * WikiText: {{template|parameter}} XML: parameter
 */
public class PositionalTemplateParameter extends WikiTextLeafNode {
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
  protected List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("value", value, false));
  }

  @Override
  public String getXMLTag() {
    return "parameter";
  }
}
