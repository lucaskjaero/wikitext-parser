package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.parse.SetupParse;

public class Library {
  public boolean someLibraryMethod() {
    return true;
  }

  public void parseWikiText(String inputText) {
    WikiTextNode tree = SetupParse.visitTreeFromText(inputText);
  }
}
