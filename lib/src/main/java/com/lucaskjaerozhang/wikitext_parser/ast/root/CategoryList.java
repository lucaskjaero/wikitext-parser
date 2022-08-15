package com.lucaskjaerozhang.wikitext_parser.ast.root;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CategoryList extends WikiTextParentNode {
  protected CategoryList(List<WikiTextNode> content) {
    super(content);
  }

  public static CategoryList from(Set<String> categories) {
    return new CategoryList(
        categories.stream()
            .map(Text::new)
            .map(c -> (WikiTextNode) new Category(Collections.singletonList((WikiTextNode) c)))
            .toList());
  }

  @Override
  public String getXMLTag() {
    return "categories";
  }

  @Override
  public String toXML() {
    return getChildren().isEmpty() ? "" : super.toXML();
  }

  public static class Category extends WikiTextParentNode {

    protected Category(List<WikiTextNode> content) {
      super(content);
    }

    @Override
    public String getXMLTag() {
      return "category";
    }
  }
}
