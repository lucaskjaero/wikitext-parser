package com.lucaskjaerozhang.wikitext_parser.objects.format;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextParentNode;
import java.util.List;

public class Italic extends WikiTextParentNode implements WikiTextNode {
  public static final String XML_TAG = "italic";

  public Italic(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
