package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.parse.SetupParse;

public class Parser {
  public static WikiTextElement parse(String inputText) {
    return SetupParse.visitTreeFromText(inputText);
  }

  public static String writeToString(WikiTextElement article) {
    return article.toXML();
  }

  public static String parseToString(String inputText) {
    return writeToString(parse(inputText));
  }

  private Parser() {
    throw new IllegalStateException("Utility class");
  }
}
