package com.lucaskjaerozhang.wikitext_parser.objects.list;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;
import java.util.Optional;

public record ListItem(List<WikiTextNode> content) implements WikiTextNode {
  public static final String XML_TAG = "listItem";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public Optional<String> getContentAsString() {
    return Optional.of(getStringValue(content));
  }
}
