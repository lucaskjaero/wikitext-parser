package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

public record Blockquote(List<WikiTextNode> content) implements WikiTextNode {
  private static final String XML_TAG = "blockquote";

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public String getContentAsString() {
    return getStringValue(content);
  }
}
