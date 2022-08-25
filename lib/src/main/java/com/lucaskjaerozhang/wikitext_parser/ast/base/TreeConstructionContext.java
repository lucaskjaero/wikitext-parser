package com.lucaskjaerozhang.wikitext_parser.ast.base;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

/**
 * Context that is passed through the tree as it is constructed. This is used to enable parent nodes
 * to control the behavior of their children. For more information, see WikiTextElement.passProps().
 */
@Builder
public class TreeConstructionContext {
  @Getter @Builder.Default @With private Boolean plainLinks = false;
}
