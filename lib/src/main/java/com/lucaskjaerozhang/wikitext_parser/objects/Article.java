package com.lucaskjaerozhang.wikitext_parser.objects;

import java.util.List;

public class Article implements WikiTextNode{
    private List<Section> sections;

    @Override
    public String getType() {
        return "Article";
    }
}
