package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;
import java.util.Map;

public record IndentedBlock(Integer level, List<WikiTextNode> content) implements WikiTextNode {
  private static final String XML_TAG = "indentedBlock";
  private static final String LEVEL_ATTRIBUTE = "level";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public String getContentAsString() {
    return getStringValue(content);
  }

  @Override
  public Map<String, String> getAttributes() {
    return Map.of(LEVEL_ATTRIBUTE, level.toString());
  }
}
