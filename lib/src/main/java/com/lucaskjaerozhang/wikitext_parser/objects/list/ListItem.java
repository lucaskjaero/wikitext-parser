package com.lucaskjaerozhang.wikitext_parser.objects.list;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ListItem extends WikiTextNodeWithInnerContent implements WikiTextNode {
  public static final String XML_TAG = "listItem";
  public static final String LEVEL_ATTRIBUTE = "level";

  private final Optional<Integer> level;

  public ListItem(Optional<Integer> level, List<WikiTextNode> content) {
    super(content);
    this.level = level;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public Map<String, String> getAttributes() {
    if (level.isEmpty()) return Map.of();
    return Map.of(LEVEL_ATTRIBUTE, level.get().toString());
  }

  public Integer getLevel() {
    return level.orElse(1);
  }
}
