package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

public class TemplateWithNoParameters extends WikiTextLeafNode {
  private final String templateName;

  public TemplateWithNoParameters(String templateName) {
    this.templateName = templateName;
  }

  @Override
  protected List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("name", templateName, false));
  }

  @Override
  public String getXMLTag() {
    return "template";
  }
}
