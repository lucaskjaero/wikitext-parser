package com.lucaskjaerozhang.wikitext_parser.ast.template.invocation;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

/**
 * A named template parameter.<br>
 * WikiText: {{template|key=value}}<br>
 * XML: parameter
 */
public class NamedTemplateParameter extends WikiTextLeafNode {
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
  protected List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("key", key, false), new NodeAttribute("value", value, false));
  }

  @Override
  public String getXMLTag() {
    return "parameter";
  }
}
