package com.lucaskjaerozhang.wikitext_parser.ast.base;

public record TreeConstructionContext(Boolean plainLinks) {
  public static TreeConstructionContext defaultContext() {
    return new TreeConstructionContext(false);
  }

  public TreeConstructionContext withSimpleLinks(Boolean newValue) {
    return new TreeConstructionContext(newValue);
  }
}
