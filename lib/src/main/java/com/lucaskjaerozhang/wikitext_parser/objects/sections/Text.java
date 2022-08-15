package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;

public class Text extends WikiTextNode implements WikiTextElement {
  public static final String XML_TAG = "text";
  private final String content;

  public Text(String content) {
    this.content = content;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public String toXML() {
    return content;
  }

  public String getContent() {
    return content;
  }
}
