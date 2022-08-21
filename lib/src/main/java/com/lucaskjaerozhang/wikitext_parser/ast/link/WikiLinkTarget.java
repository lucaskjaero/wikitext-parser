package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.TreeConstructionContext;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Wiki links look like [[wiki:language:article#section|display]], and the first half before the
 * pipe is the target.
 *
 * @param wholeLink What the user actually typed for the link.
 * @param wiki A wiki for cross wiki linking.
 * @param language A link to the article in a different language.
 * @param article The article that's being linked to
 * @param section A section of the page we're linking to.
 */
public record WikiLinkTarget(
    String wholeLink,
    Optional<String> wiki,
    Optional<String> language,
    String article,
    Optional<String> section,
    boolean isCategory)
    implements WikiTextElement {
  @Override
  public String toXML() {
    throw new UnsupportedOperationException("Intermediate type");
  }

  /**
   * Creates a wikilink target
   *
   * @param wholeLink The whole text for the link. This is used for un-named links.
   * @param namespaceComponents The namespace components that locate the article to link.
   * @param article The article name we're linking to, with no namespace components.
   * @param section An optional section to link to within the article.
   * @return A WikiLinkTarget node
   */
  public static WikiLinkTarget from(
      String wholeLink,
      List<WikiLinkNamespaceComponent> namespaceComponents,
      String article,
      Optional<String> section) {
    Map<WikiLinkNamespaceComponentType, String> components =
        namespaceComponents.stream()
            .collect(
                Collectors.toUnmodifiableMap(
                    WikiLinkNamespaceComponent::getType, WikiLinkNamespaceComponent::getComponent));

    return new WikiLinkTarget(
        wholeLink,
        Optional.ofNullable(components.get(WikiLinkNamespaceComponentType.WIKI)),
        Optional.ofNullable(components.get(WikiLinkNamespaceComponentType.LANGUAGE)),
        article,
        section,
        components.containsKey(WikiLinkNamespaceComponentType.CATEGORY));
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    /* There's nothing to pass down */
  }

  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitWikiLinkTarget(this);
  }
}
