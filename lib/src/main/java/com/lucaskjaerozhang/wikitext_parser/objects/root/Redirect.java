package com.lucaskjaerozhang.wikitext_parser.objects.root;

import com.lucaskjaerozhang.wikitext_parser.objects.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextLeafNode;
import com.lucaskjaerozhang.wikitext_parser.objects.link.WikiLink;
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
