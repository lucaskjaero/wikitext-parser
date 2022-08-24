package com.lucaskjaerozhang.wikitext_parser.visitor;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.format.Bold;
import com.lucaskjaerozhang.wikitext_parser.ast.format.Italic;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.IndentedBlock;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.LineBreak;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.XMLContainerElement;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.XMLStandaloneElement;
import com.lucaskjaerozhang.wikitext_parser.ast.link.*;
import com.lucaskjaerozhang.wikitext_parser.ast.list.ListItem;
import com.lucaskjaerozhang.wikitext_parser.ast.list.WikiTextList;
import com.lucaskjaerozhang.wikitext_parser.ast.root.Article;
import com.lucaskjaerozhang.wikitext_parser.ast.root.CategoryList;
import com.lucaskjaerozhang.wikitext_parser.ast.root.Redirect;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.HorizontalRule;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Section;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.NamedTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.PositionalTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.TemplateWithNoParameters;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.TemplateWithParameters;
import java.util.List;
import java.util.Optional;

/**
 * Provides a visitor implementation that does the minimum needed to let you skip implementation
 * steps. For a leaf node, it returns Optional.empty(). For a parent node, it returns the value of
 * the last non-empty child.
 *
 * @param <T> Whatever you want your visitor to return.
 */
public abstract class WikiTextBaseASTVisitor<T> implements WikiTextASTVisitor<T> {
  @Override
  public Optional<T> visitArticle(Article article) {
    return visitChildren(article.getChildren());
  }

  @Override
  public Optional<T> visitBold(Bold bold) {
    return visitChildren(bold.getChildren());
  }

  @Override
  public Optional<T> visitCategory(CategoryList.Category category) {
    return visitChildren(category.getChildren());
  }

  @Override
  public Optional<T> visitCategoryLink(CategoryLink categoryLink) {
    return visitChildren(categoryLink.getChildren());
  }

  @Override
  public Optional<T> visitCategoryList(CategoryList categoryList) {
    return visitChildren(categoryList.getChildren());
  }

  @Override
  public Optional<T> visitExternalLink(ExternalLink externalLink) {
    return visitChildren(externalLink.getChildren());
  }

  @Override
  public Optional<T> visitHorizontalRule(HorizontalRule horizontalRule) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitIndentedBlock(IndentedBlock indentedBlock) {
    return visitChildren(indentedBlock.getChildren());
  }

  @Override
  public Optional<T> visitItalic(Italic italic) {
    return visitChildren(italic.getChildren());
  }

  @Override
  public Optional<T> visitLineBreak(LineBreak lineBreak) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitListItem(ListItem listItem) {
    return visitChildren(listItem.getChildren());
  }

  @Override
  public Optional<T> visitNamedTemplateParameter(NamedTemplateParameter namedTemplateParameter) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitNodeAttribute(NodeAttribute nodeAttribute) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitPositionalTemplateParameter(
      PositionalTemplateParameter positionalTemplateParameter) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitRedirect(Redirect redirect) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitSection(Section section) {
    return visitChildren(section.getChildren());
  }

  @Override
  public Optional<T> visitTemplateWithNoParameters(
      TemplateWithNoParameters templateWithNoParameters) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitTemplateWithParameters(TemplateWithParameters templateWithParameters) {
    return visitChildren(templateWithParameters.getChildren());
  }

  @Override
  public Optional<T> visitText(Text text) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitUnnamedExternalLink(UnnamedExternalLink unnamedExternalLink) {
    return Optional.empty();
  }

  @Override
  public Optional<T> visitWikiLink(WikiLink wikiLink) {
    return visitChildren(wikiLink.getChildren());
  }

  @Override
  public Optional<T> visitWikiTextList(WikiTextList wikiTextList) {
    return visitChildren(wikiTextList.getChildren());
  }

  @Override
  public Optional<T> visitXMLContainerElement(XMLContainerElement element) {
    return visitChildren(element.getChildren());
  }

  @Override
  public Optional<T> visitXMLStandaloneElement(XMLStandaloneElement element) {
    return Optional.empty();
  }

  protected Optional<T> visitChildren(List<WikiTextNode> children) {
    Optional<T> child = Optional.empty();
    for (WikiTextNode c : children) {
      Optional<T> result = c.accept(this);
      if (result.isPresent()) {
        child = result;
      }
    }
    return child;
  }
}
