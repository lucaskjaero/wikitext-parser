package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;

/**
 * A special type of wikilink that places articles within categories.<br>
 * WikiText: [[:category]] or [[:category|display]]<br>
 * XML: category
 */
public class CategoryLink extends WikiLink {
  private final String category;
  @Getter private final boolean visible;

  /**
   * Creates a category link.
   *
   * @param linkTarget Which article to link to.
   * @param linkText What text to display on the link
   * @param visible Whether the link should be visible at all.
   */
  public CategoryLink(WikiLinkTarget linkTarget, List<WikiTextNode> linkText, boolean visible) {
    super(linkTarget, linkText);
    this.category = linkTarget.wholeLink();
    this.visible = visible;
  }

  @Override
  public Set<String> getCategories() {
    return Set.of(category);
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitCategoryLink(this);
  }
}
