package com.lucaskjaerozhang.wikitext_parser.objects.format;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextParentNode;
import java.util.List;

public class Bold extends WikiTextParentNode implements WikiTextNode {
  public static final String XML_TAG = "bold";

  public Bold(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
