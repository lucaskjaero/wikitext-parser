package com.lucaskjaerozhang.wikitext_parser.objects.temporary;

import com.lucaskjaerozhang.wikitext_parser.objects.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

import java.util.List;

/**
 * Basically exists because a List<Section> won't pass a type check
 * @param sections A group of sections to pass between visitor methods
 */
public record SectionGroup(List<Section> sections) implements WikiTextNode {
    @Override
    public String getType() {
        return "List<Section>";
    }
}
