package com.lucaskjaerozhang.wikitext_parser.ast.magic;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

public class ParserFunctionParameter extends WikiTextParentNode {
  public ParserFunctionParameter(List<WikiTextNode> children) {
    super(children);
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitParserFunctionParameter(this);
  }
}
