package com.lucaskjaerozhang.wikitext_parser.ast.root;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import com.lucaskjaerozhang.wikitext_parser.ast.link.WikiLink;
import java.util.List;

public class Redirect extends WikiTextLeafNode implements WikiTextElement {
  public static final String XML_TAG = "redirect";

  private final WikiLink redirectTo;

  public Redirect(WikiLink redirectTo) {
    this.redirectTo = redirectTo;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  protected List<NodeAttribute> getAttributes() {
    return redirectTo.getAttributes();
  }
}
