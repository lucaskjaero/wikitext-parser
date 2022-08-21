package com.lucaskjaerozhang.wikitext_parser.ast.link;

/**
 * An enum representing the types of WikiLink Namespaces. See WikiLinkNamespaceComponent for use.
 */
public enum WikiLinkNamespaceComponentType {
  /** Places the article in a category */
  CATEGORY,
  /** An article in a different language. */
  LANGUAGE,
  /** The article's namespace. */
  NAMESPACE,
  /** Whether the article is in a different wiki or not. */
  WIKI,
}
