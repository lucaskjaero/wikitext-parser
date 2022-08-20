package com.lucaskjaerozhang.wikitext_parser.ast.list;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import java.util.List;
import java.util.Optional;

/**
 * An item within a list<br>
 * WikiText: * item, # item, or :item<br>
 * XML: listItem
 */
public class ListItem extends WikiTextParentNode implements WikiTextElement {
  public static final String XML_TAG = "listItem";
  public static final String LEVEL_ATTRIBUTE = "level";

  private final Optional<Integer> level;

  /**
   * Create a list item.
   *
   * @param level The item level. This happens because items can contain items.
   * @param content The child nodes of the list item.
   */
  public ListItem(Optional<Integer> level, List<WikiTextNode> content) {
    super(content);
    this.level = level;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    if (level.isEmpty()) return List.of();
    return List.of(new NodeAttribute(LEVEL_ATTRIBUTE, level.get().toString(), false));
  }

  /**
   * List items can have sublists. The level represents whether this is the case.
   *
   * @return The list level.
   */
  public Integer getLevel() {
    return level.orElse(1);
  }
}
