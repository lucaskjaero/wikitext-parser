package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

public record Section(String title, Integer level, List<WikiTextNode> content)
    implements WikiTextNode {
  public static final String TYPE = "Section";

  public Section(String title, Integer level, List<WikiTextNode> content) {
    this.title = title.trim();
    this.level = level;
    this.content = content;
  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String toString() {
    return String.format(
        "<section title='%s' level='%s'>%s</section>", title, level, getStringValue(content));
  }
}
