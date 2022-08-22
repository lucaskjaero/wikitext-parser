package com.lucaskjaerozhang.wikitext_parser.xml;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.ast.format.Bold;
import com.lucaskjaerozhang.wikitext_parser.ast.format.Italic;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.IndentedBlock;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.LineBreak;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.XMLContainerElement;
import com.lucaskjaerozhang.wikitext_parser.ast.layout.XMLStandaloneElement;
import com.lucaskjaerozhang.wikitext_parser.ast.link.*;
import com.lucaskjaerozhang.wikitext_parser.ast.list.ListItem;
import com.lucaskjaerozhang.wikitext_parser.ast.list.WikiTextList;
import com.lucaskjaerozhang.wikitext_parser.ast.magic.ParserFunction;
import com.lucaskjaerozhang.wikitext_parser.ast.magic.ParserFunctionParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.root.Article;
import com.lucaskjaerozhang.wikitext_parser.ast.root.CategoryList;
import com.lucaskjaerozhang.wikitext_parser.ast.root.Redirect;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.HorizontalRule;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Section;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import com.lucaskjaerozhang.wikitext_parser.ast.template.definition.NamedTemplateParameterSubstitution;
import com.lucaskjaerozhang.wikitext_parser.ast.template.definition.PositionalTemplateParameterSubstitution;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.NamedTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.PositionalTemplateParameter;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.TemplateWithNoParameters;
import com.lucaskjaerozhang.wikitext_parser.ast.template.invocation.TemplateWithParameters;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextBaseASTVisitor;
import java.util.List;
import java.util.Optional;

public class XMLWriter extends WikiTextBaseASTVisitor<String> {
  private static final String ARTICLE_TAG = "article";
  private static final String BOLD_TAG = "bold";
  private static final String CATEGORY_TAG = "category";
  private static final String CATEGORY_LINK_TAG = "category";
  private static final String CATEGORY_LIST_TAG = "categories";
  private static final String EXTERNAL_LINK_TAG = "link";
  private static final String HORIZONTAL_RULE_TAG = "horizontalRule";
  private static final String INDENTED_BLOCK_TAG = "indentedBlock";
  private static final String ITALIC_TAG = "italic";
  private static final String LINE_BREAK_TAG = "br";
  private static final String LIST_ITEM_TAG = "listItem";
  private static final String PARSER_FUNCTION_TAG = "parserFunction";
  private static final String PARSER_FUNCTION_PARAMETER_TAG = "parserFunctionParameter";
  private static final String REDIRECT_TAG = "redirect";
  private static final String SECTION_TAG = "section";
  private static final String TEMPLATE_TAG = "template";
  private static final String TEMPLATE_PARAMETER_SUBSTITUTION = "templateParameter";
  private static final String TEMPLATE_PARAMETER_TAG = "parameter";
  private static final String WIKILINK_TAG = "wikilink";
  private static final String WIKI_LIST_TAG = "list";

  @Override
  public Optional<String> visitArticle(Article article) {
    return serializeParentNode(ARTICLE_TAG, article);
  }

  @Override
  public Optional<String> visitBold(Bold bold) {
    return serializeParentNode(BOLD_TAG, bold);
  }

  @Override
  public Optional<String> visitCategory(CategoryList.Category category) {
    return serializeParentNode(CATEGORY_TAG, category);
  }

  @Override
  public Optional<String> visitCategoryLink(CategoryLink categoryLink) {
    return categoryLink.isVisible()
        ? serializeParentNode(CATEGORY_LINK_TAG, categoryLink)
        : Optional.empty();
  }

  @Override
  public Optional<String> visitCategoryList(CategoryList categoryList) {
    return categoryList.getChildren().isEmpty()
        ? Optional.empty()
        : serializeParentNode(CATEGORY_LIST_TAG, categoryList);
  }

  @Override
  public Optional<String> visitExternalLink(ExternalLink externalLink) {
    return serializeParentNode(EXTERNAL_LINK_TAG, externalLink);
  }

  @Override
  public Optional<String> visitHorizontalRule(HorizontalRule horizontalRule) {
    return serializeLeafNode(HORIZONTAL_RULE_TAG, horizontalRule);
  }

  @Override
  public Optional<String> visitIndentedBlock(IndentedBlock indentedBlock) {
    return serializeParentNode(INDENTED_BLOCK_TAG, indentedBlock);
  }

  @Override
  public Optional<String> visitItalic(Italic italic) {
    return serializeParentNode(ITALIC_TAG, italic);
  }

  @Override
  public Optional<String> visitLineBreak(LineBreak lineBreak) {
    return serializeLeafNode(LINE_BREAK_TAG, lineBreak);
  }

  @Override
  public Optional<String> visitListItem(ListItem listItem) {
    return serializeParentNode(LIST_ITEM_TAG, listItem);
  }

  @Override
  public Optional<String> visitNamedTemplateParameter(
      NamedTemplateParameter namedTemplateParameter) {
    return serializeLeafNode(TEMPLATE_PARAMETER_TAG, namedTemplateParameter);
  }

  @Override
  public Optional<String> visitNodeAttribute(NodeAttribute nodeAttribute) {
    return nodeAttribute.usesDoubleQuotes()
        ? Optional.of(String.format("%s=\"%s\"", nodeAttribute.key(), nodeAttribute.value()))
        : Optional.of(String.format("%s='%s'", nodeAttribute.key(), nodeAttribute.value()));
  }

  @Override
  public Optional<String> visitParserFunction(ParserFunction parserFunction) {
    return serializeParentNode(PARSER_FUNCTION_TAG, parserFunction);
  }

  @Override
  public Optional<String> visitParserFunctionParameter(
      ParserFunctionParameter parserFunctionParameter) {
    return serializeParentNode(PARSER_FUNCTION_PARAMETER_TAG, parserFunctionParameter);
  }

  @Override
  public Optional<String> visitPositionalTemplateParameter(
      PositionalTemplateParameter positionalTemplateParameter) {
    return serializeLeafNode(TEMPLATE_PARAMETER_TAG, positionalTemplateParameter);
  }

  @Override
  public Optional<String> visitRedirect(Redirect redirect) {
    return serializeLeafNode(REDIRECT_TAG, redirect);
  }

  @Override
  public Optional<String> visitSection(Section section) {
    return serializeParentNode(SECTION_TAG, section);
  }

  @Override
  public Optional<String> visitNamedTemplateParameterSubstitution(
      NamedTemplateParameterSubstitution namedTemplateParameterSubstitution) {
    return serializeLeafNode(TEMPLATE_PARAMETER_SUBSTITUTION, namedTemplateParameterSubstitution);
  }

  @Override
  public Optional<String> visitPositionalTemplateParameterSubstitution(
      PositionalTemplateParameterSubstitution positionalTemplateParameterSubstitution) {
    return serializeLeafNode(
        TEMPLATE_PARAMETER_SUBSTITUTION, positionalTemplateParameterSubstitution);
  }

  @Override
  public Optional<String> visitTemplateWithNoParameters(
      TemplateWithNoParameters templateWithNoParameters) {
    return serializeLeafNode(TEMPLATE_TAG, templateWithNoParameters);
  }

  @Override
  public Optional<String> visitTemplateWithParameters(
      TemplateWithParameters templateWithParameters) {
    return serializeParentNode(TEMPLATE_TAG, templateWithParameters);
  }

  @Override
  public Optional<String> visitText(Text text) {
    return Optional.of(text.getContent());
  }

  @Override
  public Optional<String> visitUnnamedExternalLink(UnnamedExternalLink unnamedExternalLink) {
    return serializeLeafNode(EXTERNAL_LINK_TAG, unnamedExternalLink);
  }

  @Override
  public Optional<String> visitWikiLink(WikiLink wikiLink) {
    return serializeParentNode(WIKILINK_TAG, wikiLink);
  }

  @Override
  public Optional<String> visitWikiTextList(WikiTextList wikiTextList) {
    return serializeParentNode(WIKI_LIST_TAG, wikiTextList);
  }

  @Override
  public Optional<String> visitXMLContainerElement(XMLContainerElement element) {
    return serializeParentNode(element.getTag(), element);
  }

  @Override
  public Optional<String> visitXMLStandaloneElement(XMLStandaloneElement element) {
    return serializeLeafNode(element.getTag(), element);
  }

  @Override
  protected Optional<String> visitChildren(List<WikiTextNode> children) {
    List<String> visitedChildren =
        children.stream().map(c -> c.accept(this)).flatMap(Optional::stream).toList();

    return (visitedChildren.isEmpty())
        ? Optional.empty()
        : Optional.of(String.join("", visitedChildren));
  }

  private Optional<String> makeAttributesString(List<NodeAttribute> attributes) {
    List<String> visitedAttributes =
        attributes.stream().sorted().map(a -> a.accept(this)).flatMap(Optional::stream).toList();
    return (visitedAttributes.isEmpty())
        ? Optional.empty()
        : Optional.of(String.join(" ", visitedAttributes));
  }

  private Optional<String> serializeParentNode(String tag, WikiTextParentNode parent) {
    if (parent.getChildren().isEmpty()) return serializeLeafNode(tag, parent);

    Optional<String> attributes = makeAttributesString(parent.getAttributes());
    return attributes.isEmpty()
        ? Optional.of(
            String.format("<%s>%s</%s>", tag, visitChildren(parent.getChildren()).orElse(""), tag))
        : Optional.of(
            String.format(
                "<%s %s>%s</%s>",
                tag, attributes.get(), visitChildren(parent.getChildren()).orElse(""), tag));
  }

  private Optional<String> serializeLeafNode(String tag, WikiTextNode node) {
    Optional<String> attributes = makeAttributesString(node.getAttributes());
    return attributes.isEmpty()
        ? Optional.of(String.format("<%s />", tag))
        : Optional.of(String.format("<%s %s />", tag, attributes.get()));
  }
}
