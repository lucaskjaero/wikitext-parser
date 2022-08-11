package com.lucaskjaerozhang.wikitext_parser.objects.sections;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

public record Section(String title, Integer level, List<WikiTextNode> content)
    implements WikiTextNode {
  public static final String TYPE = "Section";

  @Override
  public String getType() {
    return TYPE;
  }
}
