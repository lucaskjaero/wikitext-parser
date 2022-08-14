package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record WikiLinkTarget(
    Optional<String> wiki, Optional<String> language, String target, String rawTarget)
    implements WikiTextNode {
  @Override
  public String getXMLTag() {
    return null;
  }

  @Override
  public String toXML() {
    throw new UnsupportedOperationException("Intermediate type");
  }

  public static WikiLinkTarget from(
      List<WikiLinkNamespaceComponent> namespaceComponents, List<String> target, String rawTarget) {
    Map<WikiLinkNamespaceComponentType, String> components =
        namespaceComponents.stream()
            .collect(
                Collectors.toUnmodifiableMap(
                    WikiLinkNamespaceComponent::getType, WikiLinkNamespaceComponent::getComponent));

    return new WikiLinkTarget(
        Optional.ofNullable(components.get(WikiLinkNamespaceComponentType.WIKI)),
        Optional.ofNullable(components.get(WikiLinkNamespaceComponentType.LANGUAGE)),
        String.join(" ", target),
        rawTarget);
  }
}
