package com.lucaskjaerozhang.wikitext_parser.visitor;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
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
import com.lucaskjaerozhang.wikitext_parser.ast.template.definition.TemplateParameterSubstitution;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.NamedTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.PositionalTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.TemplateWithNoParameters;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.TemplateWithParameters;
import java.util.Optional;

public interface WikiTextASTVisitor<T> {
  Optional<T> visitArticle(Article article);

  Optional<T> visitBold(Bold bold);

  Optional<T> visitCategory(CategoryList.Category category);

  Optional<T> visitCategoryLink(CategoryLink categoryLink);

  Optional<T> visitCategoryList(CategoryList categoryList);

  Optional<T> visitExternalLink(ExternalLink externalLink);

  Optional<T> visitHorizontalRule(HorizontalRule horizontalRule);

  Optional<T> visitIndentedBlock(IndentedBlock indentedBlock);

  Optional<T> visitItalic(Italic italic);

  Optional<T> visitLineBreak(LineBreak lineBreak);

  Optional<T> visitListItem(ListItem listItem);

  Optional<T> visitNamedTemplateParameter(NamedTemplateParameter namedTemplateParameter);

  Optional<T> visitNodeAttribute(NodeAttribute nodeAttribute);

  Optional<T> visitPositionalTemplateParameter(
      PositionalTemplateParameter positionalTemplateParameter);

  Optional<T> visitRedirect(Redirect redirect);

  Optional<T> visitSection(Section section);

  Optional<T> visitTemplateParameterSubstitution(
      TemplateParameterSubstitution templateParameterSubstitution);

  Optional<T> visitTemplateWithNoParameters(TemplateWithNoParameters templateWithNoParameters);

  Optional<T> visitTemplateWithParameters(TemplateWithParameters templateWithParameters);

  Optional<T> visitText(Text text);

  Optional<T> visitUnnamedExternalLink(UnnamedExternalLink unnamedExternalLink);

  Optional<T> visitWikiLink(WikiLink wikiLink);

  Optional<T> visitWikiTextList(WikiTextList wikiTextList);

  Optional<T> visitXMLContainerElement(XMLContainerElement element);

  Optional<T> visitXMLStandaloneElement(XMLStandaloneElement element);
}
