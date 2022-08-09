package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.objects.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;
import java.util.stream.Collectors;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {
  @Override
  public WikiTextNode visitRoot(WikiTextParser.RootContext ctx) {
    List<Section> sections =
        ctx.sectionContent().stream()
            .map(node -> (Section) visit(node))
            .collect(Collectors.toList());

    Article article = new Article();
    article.setSections(sections);
    return article;
  }
}
