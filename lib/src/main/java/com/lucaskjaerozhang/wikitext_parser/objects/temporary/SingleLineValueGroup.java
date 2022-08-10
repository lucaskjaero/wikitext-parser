package com.lucaskjaerozhang.wikitext_parser.objects.temporary;

import com.lucaskjaerozhang.wikitext_parser.objects.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.SingleLineValue;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

import java.util.List;

/**
 * Basically exists because a List<SingleLineValue> won't pass a type check
 *
 * @param sections A group of sections to pass between visitor methods
 */
public record SingleLineValueGroup(List<SingleLineValue> values) implements WikiTextNode {
  @Override
  public String getType() {
    return "List<SingleLineValue>";
  }
}
