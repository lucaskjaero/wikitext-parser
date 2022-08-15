package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import java.util.List;

public class IndentedBlock extends WikiTextParentNode implements WikiTextElement {
  public static final String XML_TAG = "indentedBlock";
  public static final String LEVEL_ATTRIBUTE = "level";

  private final Integer level;

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

  public Integer getLevel() {
    return level;
  }
}
