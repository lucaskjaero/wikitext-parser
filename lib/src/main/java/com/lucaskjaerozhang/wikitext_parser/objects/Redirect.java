package com.lucaskjaerozhang.wikitext_parser.objects;

import com.lucaskjaerozhang.wikitext_parser.objects.link.WikiLink;

import java.util.List;

public class Redirect implements WikiTextNode {
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
  public String toXML() {
    return String.format("<%s %s/>", getXMLTag(), redirectTo.getAttributesString());
  }
}
