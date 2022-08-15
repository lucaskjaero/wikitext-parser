package com.lucaskjaerozhang.wikitext_parser.objects.format;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextParentNode;
import java.util.List;

public class Bold extends WikiTextParentNode implements WikiTextElement {
  public static final String XML_TAG = "bold";

  public Bold(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
