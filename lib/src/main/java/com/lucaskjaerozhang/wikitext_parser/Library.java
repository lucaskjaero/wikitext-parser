package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.parse.SetupParse;

public class Library {
  public boolean someLibraryMethod() {
    return true;
  }

  public Article parseWikiText(String inputText) {
    Article article = (Article) SetupParse.visitTreeFromText(inputText);
    System.out.println(article.getType());
    return article;
  }
}
