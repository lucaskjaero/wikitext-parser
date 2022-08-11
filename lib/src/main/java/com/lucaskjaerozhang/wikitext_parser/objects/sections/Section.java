package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

public class Section implements WikiTextNode {
  public static final String TYPE = "Section";

  private String title;
  private Integer level;
  private List<WikiTextNode> content;

  public Section(String title, Integer level, List<WikiTextNode> content) {
    this.title = title.trim();
    this.level = level;
    this.content = content;
  }

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
