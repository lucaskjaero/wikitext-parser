package com.lucaskjaerozhang.wikitext_parser.objects.format;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;
import java.util.List;

public class Italic extends WikiTextNodeWithInnerContent implements WikiTextNode {
  public static final String XML_TAG = "italic";

  public Italic(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
