package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNodeWithInnerContent;

import java.util.List;

/*
 * Address links to other wikis.
 *
 * Why can't this just be a normal link?
 * Because you don't have to specify the wiki if you're linking within the same wiki,
 * and we don't have that information.
 */
public class WikiLink implements WikiTextNode {
  private final String linkTarget;
  private final String linkText;

  public WikiLink(String linkTarget, String linkText) {
    this.linkTarget = linkTarget;
    this.linkText = linkText;
  }

  public WikiLink(String linkTarget) {
    this.linkTarget = linkTarget;
    this.linkText = linkTarget;
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
