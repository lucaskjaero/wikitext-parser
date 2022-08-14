package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

/*
 * Address links to other wikis.
 *
 * Why can't this just be a normal link?
 * Because you don't have to specify the wiki if you're linking within the same wiki,
 * and we don't have that information.
 */
public class WikiLink implements WikiTextNode {
  private final WikiLinkTarget linkTarget;
  private final String linkText;

  public WikiLink(WikiLinkTarget linkTarget, String linkText) {
    this.linkTarget = linkTarget;
    this.linkText = linkText;
  }

  @Override
  public String getXMLTag() {
    return "wikilink";
  }

  @Override
  public String toXML() {
    return String.format("<%s target='%s'>%s</%s>", getXMLTag(), linkTarget, linkText, getXMLTag());
  }
}
