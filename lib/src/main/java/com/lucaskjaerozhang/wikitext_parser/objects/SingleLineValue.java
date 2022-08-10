package com.lucaskjaerozhang.wikitext_parser.objects;

public record SingleLineValue(WikiTextNode value) implements WikiTextNode{
    @Override
    public String getType() {
        return "SingleLineValue";
    }
}
