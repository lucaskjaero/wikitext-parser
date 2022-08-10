package com.lucaskjaerozhang.wikitext_parser.objects.temporary;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

public record TextNode(String contents) implements WikiTextNode {
    @Override
    public String getType() {
        return "Text";
    }
}
