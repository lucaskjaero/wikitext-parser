package com.lucaskjaerozhang.wikitext_parser.ast.template.definition;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Optional;

public class TemplateParameterSubstitution extends WikiTextNode {

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitTemplateParameterSubstitution(this);
  }
}
