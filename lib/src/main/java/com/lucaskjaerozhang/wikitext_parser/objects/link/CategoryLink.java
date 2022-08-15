package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;

import java.util.List;
import java.util.Set;

/** Categories are just a special case of a link. */
public class CategoryLink extends WikiLink {
  public static final String XML_TAG = "category";
  private final String category;
  private final boolean visible;

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
}
