package com.lucaskjaerozhang.wikitext_parser.ast.root;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A list of categories that the article belongs to. This is a special child of the root article
 * that is always one level below the root node.
 */
public class CategoryList extends WikiTextParentNode {
  /**
   * Creates a category list from a list of nodes.
   *
   * @param content The category nodes
   */
  protected CategoryList(List<WikiTextNode> content) {
    super(content);
  }

  /**
   * Convenience method to create a list of categories.
   *
   * @param categories The article categories as strings.
   * @return The category node.
   */
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
  public <T> T accept(WikiTextASTVisitor<? extends T> visitor) {
    return visitor.visitCategoryList(this);
  }

  @Override
  public String toXML() {
    return getChildren().isEmpty() ? "" : super.toXML();
  }

  /** Categories contained within the list. This is primarily used for XML output. */
  public static class Category extends WikiTextParentNode {

    /**
     * Creates one category node.
     *
     * @param content The contents of the category. (Should be a Text node)
     */
    protected Category(List<WikiTextNode> content) {
      super(content);
    }

    @Override
    public String getXMLTag() {
      return "category";
    }

    @Override
    public <T> T accept(WikiTextASTVisitor<? extends T> visitor) {
      return visitor.visitCategory(this);
    }
  }
}
