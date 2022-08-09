package com.lucaskjaerozhang.wikitext_parser.objects;

public class Section implements WikiTextNode {
    @Override
    public String getType() {
        return "Section";
    }
}
