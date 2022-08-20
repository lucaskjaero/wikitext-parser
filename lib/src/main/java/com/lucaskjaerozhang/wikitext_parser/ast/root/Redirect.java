package com.lucaskjaerozhang.wikitext_parser.ast.root;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextLeafNode;
import com.lucaskjaerozhang.wikitext_parser.ast.link.WikiLink;
import java.util.List;

/** Alternative to an article indicating the article redirects to another one. */
public class Redirect extends WikiTextLeafNode implements WikiTextElement {
  public static final String XML_TAG = "redirect";

  private final WikiLink redirectTo;

  /**
   * Creates a redirect.
   *
   * @param redirectTo The article to redirect to.
   */
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
