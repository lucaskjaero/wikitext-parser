package com.lucaskjaerozhang.wikitext_parser.objects.list;

import com.lucaskjaerozhang.wikitext_parser.objects.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextParentNode;
import java.util.List;
import java.util.Optional;

// Intentionally calling this a WikiTextList to avoid colliding with java.util.List
public class WikiTextList extends WikiTextParentNode implements WikiTextNode {
  public static final String XML_TAG = "list";
  public static final String LIST_TYPE_ATTRIBUTE = "type";
  public static final String LIST_TITLE_ATTRIBUTE = "title";

  private final ListType type;
  private final Optional<String> title;

  public WikiTextList(ListType type, List<WikiTextNode> content) {
    super(content);
    this.type = type;
    this.title = Optional.empty();
  }

  public WikiTextList(ListType type, Optional<String> title, List<WikiTextNode> content) {
    super(content);
    this.type = type;
    this.title = title.map(String::trim);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
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
