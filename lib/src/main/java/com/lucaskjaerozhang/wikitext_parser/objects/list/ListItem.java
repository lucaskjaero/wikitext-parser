package com.lucaskjaerozhang.wikitext_parser.objects.list;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;
import java.util.List;

public class ListItem extends WikiTextNodeWithInnerContent implements WikiTextNode {
  public static final String XML_TAG = "listItem";

  public ListItem(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
