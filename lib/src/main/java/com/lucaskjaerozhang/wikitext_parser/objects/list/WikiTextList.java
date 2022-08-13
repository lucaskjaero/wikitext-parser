package com.lucaskjaerozhang.wikitext_parser.objects.list;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Intentionally calling this a WikiTextList to avoid colliding with java.util.List
public record WikiTextList(ListType type, Optional<String> title, List<WikiTextNode> content)
    implements WikiTextNode {
  public static final String XML_TAG = "list";
  public static final String LIST_TYPE_ATTRIBUTE = "type";
  public static final String LIST_TITLE_ATTRIBUTE = "title";

  public WikiTextList(ListType type, Optional<String> title, List<WikiTextNode> content) {
    this.type = type;
    this.title = title.map(String::trim);
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
    if (title.isEmpty()) return Map.of(LIST_TYPE_ATTRIBUTE, type.toString());
    return Map.of(LIST_TYPE_ATTRIBUTE, type.toString(), LIST_TITLE_ATTRIBUTE, title.get());
  }
}
