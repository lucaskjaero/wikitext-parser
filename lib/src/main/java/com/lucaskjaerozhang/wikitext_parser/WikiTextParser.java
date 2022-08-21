package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;

/** The main class consumers of this library should use. */
public class WikiTextParser {
  /**
   * Generate the AST of a string input.
   *
   * @param inputText The input to parse.
   * @return The AST generated from the input.
   */
  public static WikiTextElement parse(String inputText) {
    return ParseTreeBuilder.visitTreeFromText(inputText);
  }

  /**
   * Writes an AST to an xml string.
   *
   * @param article The AST.
   * @return The AST as a string.
   */
  public static String writeToString(WikiTextElement article) {
    return article.toXML();
  }

  /**
   * Parses the wikitext to XML.
   *
   * @param inputText The test to parse.
   * @return XML representing the input.
   */
  public static String parseToString(String inputText) {
    return writeToString(parse(inputText));
  }
}
