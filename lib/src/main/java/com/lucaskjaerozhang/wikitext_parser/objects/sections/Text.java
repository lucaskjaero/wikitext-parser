package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.Optional;

public record Text(String content) implements WikiTextNode {
  public static final String XML_TAG = "text";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public Optional<String> getContentAsString() {
    return Optional.of(content);
  }

  @Override
  public String toXML() {
    return content;
  }
}
