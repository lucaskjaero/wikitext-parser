package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import java.util.List;

/**
 * A link that goes outside of the wiki which gets automatic numbering rather than display text.<br>
 * WikiText: [link]<br>
 * XML: link
 */
public class UnnamedExternalLink extends WikiTextLeafNode {
  private static final String XML_TAG = "link";
  private final String href;
  private final Boolean hasArrow;

  /**
   * Creates an external link without a name.
   *
   * @param href The link target
   * @param hasArrow Whether an arrow should be displayed on the link.
   */
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
