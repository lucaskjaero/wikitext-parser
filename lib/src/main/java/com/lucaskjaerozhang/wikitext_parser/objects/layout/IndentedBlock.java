package com.lucaskjaerozhang.wikitext_parser.objects.layout;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

public record IndentedBlock(int level, List<WikiTextNode> content) implements WikiTextNode {
  @Override
  public String getType() {
    return "IndentedBlock";
  }

  @Override
  public String toString() {
    return String.format(
        "<indentedBlock level=%s>%s</indentedBlock>", level, getStringValue(content));
  }
}
