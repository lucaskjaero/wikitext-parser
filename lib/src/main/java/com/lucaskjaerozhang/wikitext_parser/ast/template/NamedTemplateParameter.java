package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

public class NamedTemplateParameter extends WikiTextLeafNode {
  private final String key;
  private final String value;

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
