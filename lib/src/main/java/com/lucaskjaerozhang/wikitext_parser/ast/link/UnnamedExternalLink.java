package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

public class UnnamedExternalLink extends WikiTextLeafNode {
  public static final String XML_TAG = "link";
  private final String href;
  private final Boolean hasArrow;

  public UnnamedExternalLink(String href, Boolean hasArrow) {
    this.href = href;
    this.hasArrow = hasArrow;
  }

  @Override
  protected List<NodeAttribute> getAttributes() {
    return List.of(
        new NodeAttribute("href", this.href, false),
        new NodeAttribute("arrow", hasArrow.toString(), false));
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
