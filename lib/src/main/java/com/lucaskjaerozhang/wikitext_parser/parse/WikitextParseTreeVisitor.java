package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
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
import com.lucaskjaerozhang.wikitext_parser.ast.list.ListType;
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
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * A visitor that converts the parse tree to the AST. Most of the business logic is within the AST
 * nodes, this is just a mapping layer.
 */
public class WikitextParseTreeVisitor extends WikiTextBaseVisitor<WikiTextElement> {

  @Override
  public WikiTextElement visitRoot(WikiTextParser.RootContext ctx) {
    if (ctx.redirect() != null) return visit(ctx.redirect());

    List<WikiTextNode> children = ctx.children.stream().map(c -> (WikiTextNode) visit(c)).toList();
    CategoryList categories =
        CategoryList.from(
            WikiTextParentNode.getFieldValuesFromChildren(
                children, WikiTextElement::getCategories));

    return Article.from(children, categories);
  }

  @Override
  public Redirect visitRedirect(WikiTextParser.RedirectContext ctx) {
    return new Redirect((WikiLink) visit(ctx.wikiLink()));
  }

  @Override
  public Section visitSectionLevelOne(WikiTextParser.SectionLevelOneContext ctx) {
    return new Section(ctx.text().getText(), 1, visit(ctx.sectionOneContent()));
  }

  @Override
  public Section visitSectionLevelTwo(WikiTextParser.SectionLevelTwoContext ctx) {
    return new Section(ctx.text().getText(), 2, visit(ctx.sectionTwoContent()));
  }

  @Override
  public Section visitSectionLevelThree(WikiTextParser.SectionLevelThreeContext ctx) {
    return new Section(ctx.text().getText(), 3, visit(ctx.sectionThreeContent()));
  }

  @Override
  public Section visitSectionLevelFour(WikiTextParser.SectionLevelFourContext ctx) {
    return new Section(ctx.text().getText(), 4, visit(ctx.sectionFourContent()));
  }

  @Override
  public Section visitSectionLevelFive(WikiTextParser.SectionLevelFiveContext ctx) {
    return new Section(ctx.text().getText(), 5, visit(ctx.sectionFiveContent()));
  }

  @Override
  public Section visitSectionLevelSix(WikiTextParser.SectionLevelSixContext ctx) {
    return new Section(ctx.text().getText(), 6, visit(ctx.sectionContent()));
  }

  @Override
  public IndentedBlock visitIndentedBlock(WikiTextParser.IndentedBlockContext ctx) {
    // Indented blocks can be nested in the grammar but we want to unpack them into one level.
    if (ctx.indentedBlock() != null) {
      IndentedBlock innerBlock = (IndentedBlock) visit(ctx.indentedBlock());
      return new IndentedBlock(innerBlock.getLevel() + 1, innerBlock.getChildren());
    } else {
      return new IndentedBlock(1, List.of((WikiTextNode) visit(ctx.text())));
    }
  }

  @Override
  public XMLContainerElement visitContainerXMLTag(WikiTextParser.ContainerXMLTagContext ctx) {
    String tag = ctx.textWithoutSpaces(0).getText();
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    List<WikiTextNode> content = visit(ctx.sectionContent());

    return new XMLContainerElement(tag, attributes, content);
  }

  @Override
  public XMLStandaloneElement visitStandaloneXMLTag(WikiTextParser.StandaloneXMLTagContext ctx) {
    String tag = ctx.textWithoutSpaces().getText();
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());

    return new XMLStandaloneElement(tag, attributes);
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public XMLContainerElement visitLowercaseCodeBlock(WikiTextParser.LowercaseCodeBlockContext ctx) {
    String tag = "code";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public XMLContainerElement visitUppercaseCodeBlock(WikiTextParser.UppercaseCodeBlockContext ctx) {
    String tag = "CODE";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public XMLContainerElement visitLowercaseSyntaxHighlightBlock(
      WikiTextParser.LowercaseSyntaxHighlightBlockContext ctx) {
    String tag = "syntaxhighlight";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public XMLContainerElement visitUppercaseSyntaxHighlightCodeBlock(
      WikiTextParser.UppercaseSyntaxHighlightCodeBlockContext ctx) {
    String tag = "SYNTAXHIGHLIGHT";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it is latex.
   */
  @Override
  public XMLContainerElement visitLowercaseMathBlock(WikiTextParser.LowercaseMathBlockContext ctx) {
    String tag = "math";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it is latex.
   */
  @Override
  public XMLContainerElement visitUppercaseMathBlock(WikiTextParser.UppercaseMathBlockContext ctx) {
    String tag = "MATH";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * <nowiki> blocks are where wikitext interpretation is explicitly turned off.
   */
  @Override
  public XMLContainerElement visitLowercaseNowikiBlock(
      WikiTextParser.LowercaseNowikiBlockContext ctx) {
    String tag = "nowiki";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * <nowiki> blocks are where wikitext interpretation is explicitly turned off.
   */
  @Override
  public XMLContainerElement visitUppercaseNowikiBlock(
      WikiTextParser.UppercaseNowikiBlockContext ctx) {
    String tag = "NOWIKI";
    List<NodeAttribute> attributes = visit(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  @Override
  public ParserFunction visitParserFunctionWithoutParameters(
      WikiTextParser.ParserFunctionWithoutParametersContext ctx) {
    return new ParserFunction(getText(ctx.parserFunctionName()), List.of());
  }

  @Override
  public ParserFunction visitParserFunctionWithParameters(
      WikiTextParser.ParserFunctionWithParametersContext ctx) {
    return new ParserFunction(
        getText(ctx.parserFunctionName()), visit(ctx.parserFunctionParameter()));
  }

  @Override
  public ParserFunctionParameter visitParserFunctionParameter(
      WikiTextParser.ParserFunctionParameterContext ctx) {
    return new ParserFunctionParameter(visit(ctx.sectionContent()));
  }

  @Override
  public PositionalTemplateParameterSubstitution visitPositionalTemplateParameterSubstitution(
      WikiTextParser.PositionalTemplateParameterSubstitutionContext ctx) {
    return new PositionalTemplateParameterSubstitution(Integer.parseInt(ctx.DIGIT().getText()));
  }

  @Override
  public NamedTemplateParameterSubstitution visitNamedTemplateParameterSubstitution(
      WikiTextParser.NamedTemplateParameterSubstitutionContext ctx) {
    return new NamedTemplateParameterSubstitution(ctx.textWithoutSpaces().getText());
  }

  @Override
  public NodeAttribute visitSingleQuoteTagAttribute(
      WikiTextParser.SingleQuoteTagAttributeContext ctx) {
    String key = ctx.tagAttributeKeyValues().getText();
    String value = getText(ctx.tagAttributeValues());
    return new NodeAttribute(key, value, false);
  }

  @Override
  public NodeAttribute visitDoubleQuoteTagAttribute(
      WikiTextParser.DoubleQuoteTagAttributeContext ctx) {
    String key = ctx.tagAttributeKeyValues().getText();
    String value = getText(ctx.tagAttributeValues());
    return new NodeAttribute(key, value, true);
  }

  @Override
  public TemplateWithNoParameters visitTemplateWithNoParameters(
      WikiTextParser.TemplateWithNoParametersContext ctx) {
    return new TemplateWithNoParameters(ctx.text().getText());
  }

  @Override
  public TemplateWithParameters visitTemplateWithParameters(
      WikiTextParser.TemplateWithParametersContext ctx) {
    List<WikiTextNode> parameters = visit(ctx.templateParameter());
    return new TemplateWithParameters(parameters, ctx.text().getText());
  }

  @Override
  public PositionalTemplateParameter visitUnnamedParameter(
      WikiTextParser.UnnamedParameterContext ctx) {
    return new PositionalTemplateParameter(ctx.templateParameterKeyValue().getText());
  }

  @Override
  public NamedTemplateParameter visitNamedParameter(WikiTextParser.NamedParameterContext ctx) {
    return new NamedTemplateParameter(
        ctx.templateParameterKeyValue().getText(), ctx.templateParameterParameterValue().getText());
  }

  @Override
  public WikiTextList visitUnorderedList(WikiTextParser.UnorderedListContext ctx) {
    return new WikiTextList(ListType.UNORDERED, visit(ctx.unorderedListItem()));
  }

  @Override
  public ListItem visitTerminalUnorderedListItem(
      WikiTextParser.TerminalUnorderedListItemContext ctx) {
    return new ListItem(Optional.of(1), visit(ctx.sectionContentNoNewline()));
  }

  @Override
  public ListItem visitEnclosingUnorderedListItem(
      WikiTextParser.EnclosingUnorderedListItemContext ctx) {
    ListItem nestedItem = (ListItem) visit(ctx.unorderedListItem());
    return new ListItem(Optional.of(nestedItem.getLevel() + 1), nestedItem.getChildren());
  }

  @Override
  public WikiTextList visitOrderedList(WikiTextParser.OrderedListContext ctx) {
    return new WikiTextList(ListType.ORDERED, visit(ctx.orderedListItem()));
  }

  @Override
  public ListItem visitTerminalOrderedListItem(WikiTextParser.TerminalOrderedListItemContext ctx) {
    return new ListItem(Optional.of(1), visit(ctx.sectionContentNoNewline()));
  }

  @Override
  public ListItem visitEnclosingOrderedListItem(
      WikiTextParser.EnclosingOrderedListItemContext ctx) {
    ListItem nestedItem = (ListItem) visit(ctx.orderedListItem());
    return new ListItem(Optional.of(nestedItem.getLevel() + 1), nestedItem.getChildren());
  }

  @Override
  public WikiTextList visitDescriptionList(WikiTextParser.DescriptionListContext ctx) {
    Text title = (Text) visit(ctx.text());
    return new WikiTextList(
        ListType.DESCRIPTION, Optional.of(title.getContent()), visit(ctx.descriptionListItem()));
  }

  @Override
  public ListItem visitDescriptionListItem(WikiTextParser.DescriptionListItemContext ctx) {
    return new ListItem(Optional.empty(), visit(ctx.sectionContentNoNewline()));
  }

  @Override
  public Bold visitBold(WikiTextParser.BoldContext ctx) {
    return new Bold(visit(ctx.sectionContent()));
  }

  @Override
  public Italic visitItalics(WikiTextParser.ItalicsContext ctx) {
    return new Italic(visit(ctx.sectionContent()));
  }

  @Override
  public WikiLink visitBaseWikiLink(WikiTextParser.BaseWikiLinkContext ctx) {
    WikiLinkTarget target = (WikiLinkTarget) visit(ctx.wikiLinkTarget());
    if (target.isCategory()) return new CategoryLink(target, target.wholeLink(), false);
    return new WikiLink(target, target.wholeLink());
  }

  @Override
  public WikiLink visitRenamedWikiLink(WikiTextParser.RenamedWikiLinkContext ctx) {
    WikiLinkTarget target = (WikiLinkTarget) visit(ctx.wikiLinkTarget());
    String display = getText(ctx.text());
    return new WikiLink(target, display);
  }

  @Override
  public WikiLink visitAutomaticallyRenamedWikiLink(
      WikiTextParser.AutomaticallyRenamedWikiLinkContext ctx) {
    WikiLinkTarget target = (WikiLinkTarget) visit(ctx.wikiLinkTarget());
    return new WikiLink(target, WikiLink.getAutomaticallyReformattedDisplayName(target));
  }

  @Override
  public CategoryLink visitVisibleCategoryLink(WikiTextParser.VisibleCategoryLinkContext ctx) {
    WikiLinkTarget target = (WikiLinkTarget) visit(ctx.wikiLinkTarget());
    return new CategoryLink(target, target.wholeLink(), true);
  }

  @Override
  public CategoryLink visitAutomaticallyRenamedCategoryLink(
      WikiTextParser.AutomaticallyRenamedCategoryLinkContext ctx) {
    WikiLinkTarget target = (WikiLinkTarget) visit(ctx.wikiLinkTarget());
    return new CategoryLink(target, WikiLink.getAutomaticallyReformattedDisplayName(target), true);
  }

  @Override
  public WikiLinkTarget visitWikiLinkTarget(WikiTextParser.WikiLinkTargetContext ctx) {
    String wholeLink = ctx.getText();
    List<WikiLinkNamespaceComponent> namespaceComponents =
        ctx.wikiLinkNamespaceComponent().stream()
            .map(n -> (WikiLinkNamespaceComponent) visit(n))
            .toList();
    String article = getText(ctx.text());
    Optional<String> section =
        ctx.wikiLinkSectionComponent() != null
            ? Optional.of(((Text) visit(ctx.wikiLinkSectionComponent())).getContent())
            : Optional.empty();

    return WikiLinkTarget.from(wholeLink, namespaceComponents, article, section);
  }

  @Override
  public WikiLinkNamespaceComponent visitWikiLinkNamespaceComponent(
      WikiTextParser.WikiLinkNamespaceComponentContext ctx) {
    return new WikiLinkNamespaceComponent(ctx.text().getText());
  }

  @Override
  public Text visitWikiLinkSectionComponent(WikiTextParser.WikiLinkSectionComponentContext ctx) {
    return new Text(getText(ctx.text()));
  }

  @Override
  public UnnamedExternalLink visitUnnamedExternalLink(
      WikiTextParser.UnnamedExternalLinkContext ctx) {
    return new UnnamedExternalLink(ctx.urlCharacters().getText(), true);
  }

  @Override
  public ExternalLink visitNamedExternalLink(WikiTextParser.NamedExternalLinkContext ctx) {
    return new ExternalLink(ctx.urlCharacters().getText(), true, getText(ctx.text()));
  }

  @Override
  public HorizontalRule visitHorizontalRule(WikiTextParser.HorizontalRuleContext ctx) {
    return new HorizontalRule();
  }

  @Override
  public Text visitText(WikiTextParser.TextContext ctx) {
    return new Text(ctx.getText());
  }

  @Override
  public LineBreak visitLineBreak(WikiTextParser.LineBreakContext ctx) {
    return new LineBreak();
  }

  @Override
  public Text visitTerminal(TerminalNode node) {
    return new Text(node.getText());
  }

  /**
   * Handles the common pattern of needing to visit every item in a list.
   *
   * @param children The list to visit.
   * @param <T> What type to return. Must extend WikiTextElement
   * @return The list, visited.
   */
  private <T extends WikiTextElement> List<T> visit(List<? extends ParserRuleContext> children) {
    return children.stream().map(c -> (T) visit(c)).toList();
  }

  /**
   * Handles the common pattern of getting a list of elements as text.
   *
   * @param children The elements to convert to text.
   * @return The elements as text, concatenated.
   */
  private String getText(List<? extends ParserRuleContext> children) {
    return children.stream().map(RuleContext::getText).collect(Collectors.joining(""));
  }
}
