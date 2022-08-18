package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

public class PositionalTemplateParameter extends WikiTextLeafNode {
  private final String value;

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
