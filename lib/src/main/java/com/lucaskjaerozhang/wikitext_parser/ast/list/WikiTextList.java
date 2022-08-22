package com.lucaskjaerozhang.wikitext_parser.ast.list;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;

/**
 * Represents a list in Wikitext.<br>
 * Intentionally calling this a WikiTextList to avoid colliding with java.util.List
 */
public class WikiTextList extends WikiTextParentNode implements WikiTextElement {
  private static final String LIST_TYPE_ATTRIBUTE = "type";
  private static final String LIST_TITLE_ATTRIBUTE = "title";

  private final ListType type;
  private final Optional<String> title;

  /**
   * Creates a list without a title.
   *
   * @param type What type of list it is.
   * @param content The list items.
   */
  public WikiTextList(ListType type, List<WikiTextNode> content) {
    super(content);
    this.type = type;
    this.title = Optional.empty();
  }

  /**
   * Creates a list with title.
   *
   * @param type What type of list it is.
   * @param title The list title.
   * @param content The list items.
   */
  public WikiTextList(ListType type, Optional<String> title, List<WikiTextNode> content) {
    super(content);
    this.type = type;
    this.title = title.map(String::trim);
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitWikiTextList(this);
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    if (title.isEmpty())
      return List.of(new NodeAttribute(LIST_TYPE_ATTRIBUTE, type.toString(), false));
    return List.of(
        new NodeAttribute(LIST_TYPE_ATTRIBUTE, type.toString(), false),
        new NodeAttribute(LIST_TITLE_ATTRIBUTE, title.get(), false));
  }
}
