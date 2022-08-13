package com.lucaskjaerozhang.wikitext_parser.objects.list;

public enum ListType {
  UNORDERED("unordered"),
  ORDERED("ordered"),
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
