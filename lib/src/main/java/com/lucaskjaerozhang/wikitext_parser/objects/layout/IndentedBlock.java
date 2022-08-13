package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;
import java.util.List;
import java.util.Map;

public class IndentedBlock extends WikiTextNodeWithInnerContent implements WikiTextNode {
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
  public Map<String, String> getAttributes() {
    return Map.of(LEVEL_ATTRIBUTE, level.toString());
  }

  public Integer getLevel() {
    return level;
  }
}
