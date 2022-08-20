package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import java.util.List;

/**
 * An indented block.<br>
 * WikiText: : text<br>
 * XML: indentedBlock level=n
 */
public class IndentedBlock extends WikiTextParentNode implements WikiTextElement {
  private static final String XML_TAG = "indentedBlock";
  private static final String LEVEL_ATTRIBUTE = "level";

  private final Integer level;

  /**
   * Constructs the indented block node.
   *
   * @param content The child nodes.
   */
  public IndentedBlock(Integer level, List<WikiTextNode> content) {
    super(content);
    this.level = level;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute(LEVEL_ATTRIBUTE, level.toString(), false));
  }

  /**
   * Text can be indented more than once, with one level per :.<br>
   * One level - : One<br>
   * Two levels - :: Two<br>
   * ... etc.
   *
   * @return The indentation level.
   */
  public Integer getLevel() {
    return level;
  }
}
