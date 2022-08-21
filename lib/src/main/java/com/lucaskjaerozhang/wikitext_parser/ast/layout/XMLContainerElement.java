package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.*;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * An XML node with children. In WikiText, you can pass through HTML or special xml tags. This
 * handles them as a group. Examples: - blockquote - poem
 */
public class XMLContainerElement extends WikiTextParentNode implements WikiTextElement {
  private final String xmlTag;
  private final List<NodeAttribute> attributes;

  /**
   * Creates an XML container node.
   *
   * @param tag The XML tag.
   * @param attributes Any xml attributes.
   * @param content Child nodes
   */
  public XMLContainerElement(
      String tag, List<NodeAttribute> attributes, List<WikiTextNode> content) {
    super(content);
    this.xmlTag = tag.trim();
    this.attributes = attributes;
  }

  @Override
  public String getXMLTag() {
    return xmlTag;
  }

  @Override
  public <T> T accept(WikiTextASTVisitor<? extends T> visitor) {
    return visitor.visitXMLContainerElement(this);
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return attributes;
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    super.passProps(updateContextFromSpanValues(context));
  }

  /*
   * If there is a <span class='plainlinks'> node, then all child link nodes are plain.
   * We add that to the context here.
   */
  private TreeConstructionContext updateContextFromSpanValues(TreeConstructionContext context) {
    Optional<String> className =
        attributes.stream()
            .filter(a -> a.key().equals("class"))
            .map(NodeAttribute::value)
            .findFirst();

    if (xmlTag.equals("span")
        && className.isPresent()
        && className.get().toLowerCase(Locale.ROOT).equals("plainlinks"))
      return context.withPlainLinks(true);

    return context;
  }
}
