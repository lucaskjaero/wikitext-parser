package com.lucaskjaerozhang.wikitext_parser.ast.format;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/**
 * Italicized text.<br>
 * WikiText: ''text''<br>
 * XML: italic
 */
public class Italic extends WikiTextParentNode implements WikiTextElement {
  private static final String XML_TAG = "italic";

  /**
   * Constructs the italic node.
   *
   * @param content The child nodes.
   */
  public Italic(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitItalic(this);
  }
}
