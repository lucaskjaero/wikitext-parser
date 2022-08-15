package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextParentNode;
import java.util.List;

public class IndentedBlock extends WikiTextParentNode implements WikiTextNode {
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
