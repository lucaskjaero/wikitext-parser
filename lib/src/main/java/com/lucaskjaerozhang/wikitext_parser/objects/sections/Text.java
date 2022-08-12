package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class Text implements WikiTextNode {
  @JacksonXmlText()
  private final String content;

  public Text(String contents) {
    this.content = contents;
  }

  public String getContent() {
    return content;
  }

  @JsonIgnore
  @Override
  public String getType() {
    return "Text";
  }
}
