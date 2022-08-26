package com.lucaskjaerozhang.wikitext_parser.ast.root;

import com.lucaskjaerozhang.wikitext_parser.ast.base.TreeConstructionContext;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/** The root of the AST, containing all content below. */
public class Article extends WikiTextParentNode implements WikiTextElement {
  /**
   * Creates a tree from the given content.
   *
   * @param content The child nodes contained in the article.
   */
  public Article(List<WikiTextNode> content) {
    super(content);
    content.forEach(c -> passProps(TreeConstructionContext.builder().build()));
  }

  /**
   * Creates an article with the given categories. Intended to be called from the visitor.
   *
   * @param content The child nodes contained in the article.
   * @param categories What categories the article falls under.
   * @return The fully constructed article.
   */
  public static Article from(List<WikiTextNode> content, CategoryList categories) {
    return new Article(
        Stream.of(List.of((WikiTextNode) categories), content)
            .flatMap(Collection::stream)
            .toList());
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitArticle(this);
  }
}
