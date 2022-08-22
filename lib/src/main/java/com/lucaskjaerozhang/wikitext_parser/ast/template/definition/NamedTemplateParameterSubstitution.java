package com.lucaskjaerozhang.wikitext_parser.ast.template.definition;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class NamedTemplateParameterSubstitution extends WikiTextNode {
  @Getter private final String parameter;

  public NamedTemplateParameterSubstitution(String parameter) {
    this.parameter = parameter;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitNamedTemplateParameterSubstitution(this);
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("parameter", parameter, false));
  }
}
