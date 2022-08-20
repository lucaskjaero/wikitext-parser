package com.lucaskjaerozhang.wikitext_parser.ast.template.definition;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;

public class TemplateParameterSubstitution extends WikiTextLeafNode {
  @Override
  public String getXMLTag() {
    return "templateParameterSubstitution";
  }
}
