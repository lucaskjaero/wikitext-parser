package com.lucaskjaerozhang.wikitext_parser.ast.sections;

import com.lucaskjaerozhang.wikitext_parser.ast.base.TreeConstructionContext;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;

/** Plain text that is not formatted in any way. */
public class Text extends WikiTextNode implements WikiTextElement {
  public static final String XML_TAG = "text";
  private final String content;

  /**
   * Creates a text node
   *
   * @param content The text within the node.
   */
  public Text(String content) {
    this.content = content;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public String toXML() {
    return content;
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    /* Nothing to pass */
  }

  /**
   * Get the content within the node.
   *
   * @return The content.
   */
  public String getContent() {
    return content;
  }
}
