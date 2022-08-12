package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.export.XMLWriter;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.parse.SetupParse;

public class WikiTextParser {
  public static Article parse(String inputText) {
    return SetupParse.visitTreeFromText(inputText);
  }

  public static String writeToString(Article article) {
    return XMLWriter.writeArticleToString(article);
  }

  public static String parseToString(String inputText) {
    return writeToString(parse(inputText));
  }
}
