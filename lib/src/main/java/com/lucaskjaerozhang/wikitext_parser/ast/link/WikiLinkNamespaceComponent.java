package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.common.metadata.WikiConstants;
import java.util.Locale;

/**
 * A prefix component of a wikilink target showing where to locate the article.<br>
 * <br>
 * Types:
 *
 * <ul>
 *   <li>category
 *   <li>language
 *   <li>namespace
 *   <li>wiki
 * </ul>
 */
public class WikiLinkNamespaceComponent implements WikiTextElement {
  private final String component;
  private final WikiLinkNamespaceComponentType type;

  /**
   * Creates a namespace component from the given string, automatically detecting what type of
   * namespace component it is.
   *
   * @param component The namespace component.
   */
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

  /**
   * Get the namespace component
   *
   * @return The namespace component as a string.
   */
  public String getComponent() {
    return component;
  }

  /**
   * This class detects what type of namespace component the input string is based on what it is.
   *
   * @return The type of namespace component.
   */
  public WikiLinkNamespaceComponentType getType() {
    return type;
  }
}
