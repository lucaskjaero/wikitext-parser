package com.lucaskjaerozhang.wikitext_parser.ast.base;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

/**
 * Context that is passed through the tree as it is constructed. This is used to enable parent nodes
 * to control the behavior of their children. For more information, see WikiTextElement.passProps().
 */
@Builder
@Getter
public class TreeConstructionContext {
  @Builder.Default private final Map<String, String> templates = Map.of();

  @Builder.Default @With private final Boolean plainLinks = false;
}
