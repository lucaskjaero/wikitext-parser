package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

/**
 * An XML node with no children. In WikiText, you can pass through HTML or special xml tags. This
 * handles them as a group. Examples: - blockquote - poem
 */
public class XMLStandaloneElement extends WikiTextNode implements WikiTextElement {
  @Getter private final String tag;
  @Getter private final List<NodeAttribute> attributes;

  /**
   * Creates an XML node with no children.
   *
   * @param tag The XML tag.
   * @param attributes Any xml attributes.
   */
  public XMLStandaloneElement(String tag, List<NodeAttribute> attributes) {
    this.tag = tag.trim();
    this.attributes = attributes;
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitXMLStandaloneElement(this);
  }
}
