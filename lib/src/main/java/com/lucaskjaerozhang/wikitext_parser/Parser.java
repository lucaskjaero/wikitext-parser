package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.parse.SetupParse;

public class Parser {
  public static WikiTextNode parse(String inputText) {
    return SetupParse.visitTreeFromText(inputText);
  }

  public static String writeToString(WikiTextNode article) {
    return article.toXML();
  }

  public static String parseToString(String inputText) {
    return writeToString(parse(inputText));
  }
}
