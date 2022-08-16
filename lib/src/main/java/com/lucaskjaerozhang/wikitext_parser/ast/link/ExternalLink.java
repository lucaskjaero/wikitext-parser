package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.TreeConstructionContext;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import java.util.List;

public class ExternalLink extends WikiTextParentNode {
  public static final String XML_TAG = "link";
  private final String href;
  private Boolean hasArrow;

  public ExternalLink(String href, boolean hasArrow, String display) {
    super(List.of(new Text(display)));
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

  @Override
  public void passProps(TreeConstructionContext context) {
    super.passProps(context);
    // reacts to being enclosed in a <span class='plainlinks'> block.
    if (Boolean.TRUE.equals(context.plainLinks())) {
      this.hasArrow = false;
    }
  }
}
