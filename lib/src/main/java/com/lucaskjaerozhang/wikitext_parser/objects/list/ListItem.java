package com.lucaskjaerozhang.wikitext_parser.objects.list;

import com.lucaskjaerozhang.wikitext_parser.objects.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;
import java.util.List;
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
  public List<NodeAttribute> getAttributes() {
    if (level.isEmpty()) return List.of();
    return List.of(new NodeAttribute(LEVEL_ATTRIBUTE, level.get().toString(), false));
  }

  public Integer getLevel() {
    return level.orElse(1);
  }
}
