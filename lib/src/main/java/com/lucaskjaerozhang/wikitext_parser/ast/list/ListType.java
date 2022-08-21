package com.lucaskjaerozhang.wikitext_parser.ast.list;

/**
 * Defines which type of list a particular list is. There are three types of lists in wikitext.<br>
 *
 * <ul>
 *   <li>Unordered
 *   <li>Ordered
 *   <li>Description lists.
 * </ul>
 */
public enum ListType {
  /** A list where the items are not ordered. */
  UNORDERED("unordered"),
  /** A list where the items are ordered. */
  ORDERED("ordered"),
  /** An unordered list where there is a description at the top. */
  DESCRIPTION("description");

  private final String type;

  ListType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }
}
