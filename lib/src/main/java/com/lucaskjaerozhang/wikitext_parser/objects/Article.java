package com.lucaskjaerozhang.wikitext_parser.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.util.List;

public class Article implements WikiTextNode {
  @JacksonXmlText()
  @JacksonXmlElementWrapper(useWrapping = false)
  private final List<WikiTextNode> content;

  public Article(List<WikiTextNode> content) {
    this.content = content;
  }

  @JsonIgnore
  @Override
  public String getType() {
    return "Article";
  }

  public List<WikiTextNode> getContent() {
    return content;
  }
}
