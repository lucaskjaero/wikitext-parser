package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.metadata.WikiConstants;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import java.util.Locale;

public class WikiLinkNamespaceComponent implements WikiTextElement {
  private final String component;
  private final WikiLinkNamespaceComponentType type;

  public WikiLinkNamespaceComponent(String component) {
    this.component = component;

    if (component.toLowerCase(Locale.ROOT).equals("category")) {
      type = WikiLinkNamespaceComponentType.CATEGORY;
    } else if (WikiConstants.LANGUAGE_CODES.contains(component.toLowerCase(Locale.ROOT))) {
      type = WikiLinkNamespaceComponentType.LANGUAGE;
    } else if (WikiConstants.WIKIS.contains(component.toLowerCase(Locale.ROOT))) {
      type = WikiLinkNamespaceComponentType.WIKI;
    } else {
      type = WikiLinkNamespaceComponentType.NAMESPACE;
    }
  }

  @Override
  public String toXML() {
    throw new UnsupportedOperationException("Intermediate type");
  }

  public String getComponent() {
    return component;
  }

  public WikiLinkNamespaceComponentType getType() {
    return type;
  }
}