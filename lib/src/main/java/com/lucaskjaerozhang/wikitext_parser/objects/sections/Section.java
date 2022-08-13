package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record Section(String title, Integer level, List<WikiTextNode> content)
    implements WikiTextNode {
  public static final String XML_TAG = "section";
  public static final String TITLE_ATTRIBUTE = "title";
  public static final String LEVEL_ATTRIBUTE = "level";

  public Section(String title, Integer level, List<WikiTextNode> content) {
    this.title = title.trim();
    this.level = level;
    this.content = content;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public Optional<String> getContentAsString() {
    return Optional.of(getStringValue(content));
  }

  @Override
  public Map<String, String> getAttributes() {
    return Map.of(TITLE_ATTRIBUTE, title, LEVEL_ATTRIBUTE, level.toString());
  }
}
