package com.lucaskjaerozhang.wikitext_parser.ast.layout;

import com.lucaskjaerozhang.wikitext_parser.ast.base.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * In WikiText, you can pass through HTML or special xml tags. This handles them as a group.
 * Examples: - blockquote - poem
 */
public class XMLContainerElement extends WikiTextParentNode implements WikiTextElement {
  public final String xmlTag;
  private final List<NodeAttribute> attributes;

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
  public List<NodeAttribute> getAttributes() {
    return attributes;
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    super.passProps(updateContextFromSpanValues(context));
  }

  private TreeConstructionContext updateContextFromSpanValues(TreeConstructionContext context) {
    Optional<String> className =
        attributes.stream()
            .filter(a -> a.key().equals("class"))
            .map(NodeAttribute::value)
            .findFirst();

    if (xmlTag.equals("span")
        && className.isPresent()
        && className.get().toLowerCase(Locale.ROOT).equals("plainlinks"))
      return context.withSimpleLinks(true);

    return context;
  }
}
