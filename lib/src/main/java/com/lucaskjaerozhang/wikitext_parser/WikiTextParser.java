package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import com.lucaskjaerozhang.wikitext_parser.xml.XMLWriter;

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
   * @param root The AST.
   * @return The AST as a string.
   */
  public static String writeToString(WikiTextNode root) {
    XMLWriter writer = new XMLWriter();
    return writer.writeXML(root);
  }

  /**
   * Parses the wikitext to XML.
   *
   * @param inputText The test to parse.
   * @return XML representing the input.
   */
  public static String parseToString(String inputText) {
    WikiTextNode parsed = (WikiTextNode) parse(inputText);
    return writeToString(parsed);
  }
}
