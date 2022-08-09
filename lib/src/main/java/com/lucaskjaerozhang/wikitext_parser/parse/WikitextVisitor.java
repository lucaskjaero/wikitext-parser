package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {
    @Override
    public WikiTextNode visitRoot(WikiTextParser.RootContext ctx) {
        return super.visitRoot(ctx);
    }
}
