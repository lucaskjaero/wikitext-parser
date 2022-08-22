package com.lucaskjaerozhang.wikitext_parser.ast.template.definition;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class PositionalTemplateParameterSubstitution extends WikiTextNode {
  @Getter private final Integer parameter;

  public PositionalTemplateParameterSubstitution(Integer parameter) {
    this.parameter = parameter;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitPositionalTemplateParameterSubstitution(this);
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("parameter", parameter.toString(), false));
  }
}
