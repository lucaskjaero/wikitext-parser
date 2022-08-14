package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.metadata.WikiConstants;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public record WikiLinkTarget(Optional<String> wiki, Optional<String> language, String target)
    implements WikiTextNode {
  @Override
  public String getXMLTag() {
    return "linktarget";
  }

  @Override
  public String toXML() {
    throw new UnsupportedOperationException("Intermediate type");
  }

  public static WikiLinkTarget from(List<String> components) {
    if (components.size() == 1) return fromLinkWithNoNamespaceComponents(components);
    if (components.size() == 2) return fromLinkWithOneNamespaceComponent(components);
    return fromLinkWithTwoNamespaceComponents(components);
  }

  private static WikiLinkTarget fromLinkWithNoNamespaceComponents(List<String> components) {
    return new WikiLinkTarget(Optional.empty(), Optional.empty(), components.get(0));
  }

  private static WikiLinkTarget fromLinkWithOneNamespaceComponent(List<String> components) {
    String first = components.get(0);
    if (WikiConstants.WIKIS.contains(first.toLowerCase(Locale.ROOT))) {
      return new WikiLinkTarget(Optional.of(first), Optional.empty(), components.get(1));
    } else if (WikiConstants.LANGUAGE_CODES.contains(first.toLowerCase(Locale.ROOT))) {
      return new WikiLinkTarget(Optional.empty(), Optional.of(first), components.get(1));
    } else {
      return new WikiLinkTarget(Optional.empty(), Optional.empty(), String.join(" ", components));
    }
  }

  private static WikiLinkTarget fromLinkWithTwoNamespaceComponents(List<String> components) {
    String first = components.get(0);
    String second = components.get(1);
    List<String> rest = components.subList(1, components.size() - 1);

    if (WikiConstants.WIKIS.contains(first.toLowerCase(Locale.ROOT))) {
      if (WikiConstants.LANGUAGE_CODES.contains(second.toLowerCase(Locale.ROOT))) {
        return new WikiLinkTarget(Optional.of(first), Optional.of(second), String.join(" ", rest));
      }
    }
    return fromLinkWithOneNamespaceComponent(components);
  }
}
