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
import com.lucaskjaerozhang.wikitext_parser.ast.template.NamedTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.PositionalTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.TemplateWithNoParameters;
import com.lucaskjaerozhang.wikitext_parser.ast.template.TemplateWithParameters;

public interface WikiTextASTVisitor<T> {
  T visitArticle(Article article);

  T visitBold(Bold bold);

  T visitCategory(CategoryList.Category category);

  T visitCategoryLink(CategoryLink categoryLink);

  T visitCategoryList(CategoryList categoryList);

  T visitExternalLink(ExternalLink externalLink);

  T visitHorizontalRule(HorizontalRule horizontalRule);

  T visitIndentedBlock(IndentedBlock indentedBlock);

  T visitItalic(Italic italic);

  T visitLineBreak(LineBreak lineBreak);

  T visitListItem(ListItem listItem);

  T visitNamedTemplateParameter(NamedTemplateParameter namedTemplateParameter);

  T visitNodeAttribute(NodeAttribute nodeAttribute);

  T visitPositionalTemplateParameter(PositionalTemplateParameter positionalTemplateParameter);

  T visitRedirect(Redirect redirect);

  T visitSection(Section section);

  T visitTemplateWithNoParameters(TemplateWithNoParameters templateWithNoParameters);

  T visitTemplateWithParameters(TemplateWithParameters templateWithParameters);

  T visitText(Text text);

  T visitUnnamedExternalLink(UnnamedExternalLink unnamedExternalLink);

  T visitWikiLink(WikiLink wikiLink);

  T visitWikiLinkNamespaceComponent(WikiLinkNamespaceComponent wikiLinkNamespaceComponent);

  T visitWikiLinkTarget(WikiLinkTarget wikiLinkTarget);

  T visitWikiTextList(WikiTextList wikiTextList);

  T visitXMLContainerElement(XMLContainerElement element);

  T visitXMLStandaloneElement(XMLStandaloneElement element);
}
