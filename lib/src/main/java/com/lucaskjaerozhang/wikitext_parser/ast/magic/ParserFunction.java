package com.lucaskjaerozhang.wikitext_parser.ast.magic;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class ParserFunction extends WikiTextParentNode {
  @Getter private final String functionName;

  public ParserFunction(String functionName, List<WikiTextNode> arguments) {
    super(arguments);
    this.functionName = functionName;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitParserFunction(this);
  }
}
