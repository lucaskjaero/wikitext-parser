package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextParentNode;
import java.util.List;

public class Section extends WikiTextParentNode implements WikiTextNode {
  public static final String XML_TAG = "section";
  public static final String TITLE_ATTRIBUTE = "title";
  public static final String LEVEL_ATTRIBUTE = "level";

  private final String title;
  private final Integer level;

  public Section(String title, Integer level, List<WikiTextNode> content) {
    super(content);
    this.title = title.trim();
    this.level = level;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(
        new NodeAttribute(TITLE_ATTRIBUTE, title, false),
        new NodeAttribute(LEVEL_ATTRIBUTE, level.toString(), false));
  }
}
