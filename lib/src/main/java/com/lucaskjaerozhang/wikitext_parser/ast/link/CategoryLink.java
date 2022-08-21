package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Set;

/**
 * A special type of wikilink that places articles within categories.<br>
 * WikiText: [[:category]] or [[:category|display]]<br>
 * XML: category
 */
public class CategoryLink extends WikiLink {
  private static final String XML_TAG = "category";
  private final String category;
  private final boolean visible;

  /**
   * Creates a category link.
   *
   * @param linkTarget Which article to link to.
   * @param linkText What text to display on the link
   * @param visible Whether the link should be visible at all.
   */
  public CategoryLink(WikiLinkTarget linkTarget, String linkText, boolean visible) {
    super(linkTarget, linkText);
    this.category = linkTarget.wholeLink();
    this.visible = visible;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public Set<String> getCategories() {
    return Set.of(category);
  }

  @Override
  public String toXML() {
    // Category links can be invisible.
    // This is done to place an article in a category.
    return visible ? super.toXML() : "";
  }

  @Override
  public <T> T accept(WikiTextASTVisitor<? extends T> visitor) {
    return visitor.visitCategoryLink(this);
  }
}
