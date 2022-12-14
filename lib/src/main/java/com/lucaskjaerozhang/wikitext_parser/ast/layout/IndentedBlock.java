package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

/**
 * An indented block.<br>
 * WikiText: : text<br>
 * Text can be indented more than once, with one level per :.<br>
 * One level - : One<br>
 * Two levels - :: Two<br>
 * ... etc.
 */
public class IndentedBlock extends WikiTextParentNode implements WikiTextElement {
  private static final String LEVEL_ATTRIBUTE = "level";

  @Getter private final Integer level;

  /**
   * Constructs the indented block node.
   *
   * @param level The level of indentation.
   * @param content The child nodes.
   */
  public IndentedBlock(Integer level, List<WikiTextNode> content) {
    super(content);
    this.level = level;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitIndentedBlock(this);
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute(LEVEL_ATTRIBUTE, level.toString()));
  }
}
