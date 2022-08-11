package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

public record Blockquote(List<WikiTextNode> content) implements WikiTextNode {
  @Override
  public String getType() {
    return "Blockquote";
  }
}
