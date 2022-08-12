package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

@JsonTypeInfo(use=DEDUCTION)
public class Section implements WikiTextNode {
  public static final String TYPE = "Section";

  @JacksonXmlProperty(isAttribute = true)
  private String title;
  @JacksonXmlProperty(isAttribute = true)
  private Integer level;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlText()
  private List<WikiTextNode> content;

  public Section(String title, Integer level, List<WikiTextNode> content) {
    this.title = title.trim();
    this.level = level;
    this.content = content;
  }

  @JsonIgnore
  @Override
  public String getType() {
    return TYPE;
  }

  public String getTitle() {
    return title;
  }

  public Integer getLevel() {
    return level;
  }

  public List<WikiTextNode> getContent() {
    return content;
  }
}
