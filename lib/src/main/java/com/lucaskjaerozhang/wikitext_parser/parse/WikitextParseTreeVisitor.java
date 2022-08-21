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
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
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
        CategoryList.from(WikiTextParentNode.getCategoriesFromChildren(children));

    return Article.from(children, categories);
  }

  @Override
  public Redirect visitRedirect(WikiTextParser.RedirectContext ctx) {
    return new Redirect((WikiLink) visit(ctx.wikiLink()));
  }

  @Override
  public Section visitSectionLevelOne(WikiTextParser.SectionLevelOneContext ctx) {
    return new Section(
        ctx.text().getText(),
        1,
        ctx.sectionOneContent().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public Section visitSectionLevelTwo(WikiTextParser.SectionLevelTwoContext ctx) {
    return new Section(
        ctx.text().getText(),
        2,
        ctx.sectionTwoContent().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public Section visitSectionLevelThree(WikiTextParser.SectionLevelThreeContext ctx) {
    return new Section(
        ctx.text().getText(),
        3,
        ctx.sectionThreeContent().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public Section visitSectionLevelFour(WikiTextParser.SectionLevelFourContext ctx) {
    return new Section(
        ctx.text().getText(),
        4,
        ctx.sectionFourContent().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public Section visitSectionLevelFive(WikiTextParser.SectionLevelFiveContext ctx) {
    return new Section(
        ctx.text().getText(),
        5,
        ctx.sectionFiveContent().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public Section visitSectionLevelSix(WikiTextParser.SectionLevelSixContext ctx) {
    return new Section(
        ctx.text().getText(),
        6,
        ctx.sectionContent().stream().map(c -> (WikiTextNode) visit(c)).toList());
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
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    List<WikiTextNode> content =
        ctx.sectionContent().stream().map(c -> (WikiTextNode) visit(c)).toList();

    return new XMLContainerElement(tag, attributes, content);
  }

  @Override
  public XMLStandaloneElement visitStandaloneXMLTag(WikiTextParser.StandaloneXMLTagContext ctx) {
    String tag = ctx.textWithoutSpaces().getText();
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();

    return new XMLStandaloneElement(tag, attributes);
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public WikiTextElement visitLowercaseCodeBlock(WikiTextParser.LowercaseCodeBlockContext ctx) {
    String tag = "code";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public WikiTextElement visitUppercaseCodeBlock(WikiTextParser.UppercaseCodeBlockContext ctx) {
    String tag = "CODE";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public WikiTextElement visitLowercaseSyntaxHighlightBlock(
      WikiTextParser.LowercaseSyntaxHighlightBlockContext ctx) {
    String tag = "syntaxhighlight";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public WikiTextElement visitUppercaseSyntaxHighlightCodeBlock(
      WikiTextParser.UppercaseSyntaxHighlightCodeBlockContext ctx) {
    String tag = "SYNTAXHIGHLIGHT";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it is latex.
   */
  @Override
  public WikiTextElement visitLowercaseMathBlock(WikiTextParser.LowercaseMathBlockContext ctx) {
    String tag = "math";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it is latex.
   */
  @Override
  public WikiTextElement visitUppercaseMathBlock(WikiTextParser.UppercaseMathBlockContext ctx) {
    String tag = "MATH";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * <nowiki> blocks are where wikitext interpretation is explicitly turned off.
   */
  @Override
  public WikiTextElement visitLowercaseNowikiBlock(WikiTextParser.LowercaseNowikiBlockContext ctx) {
    String tag = "nowiki";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  /*
   * <nowiki> blocks are where wikitext interpretation is explicitly turned off.
   */
  @Override
  public WikiTextElement visitUppercaseNowikiBlock(WikiTextParser.UppercaseNowikiBlockContext ctx) {
    String tag = "NOWIKI";
    List<NodeAttribute> attributes =
        ctx.tagAttribute().stream().map(c -> (NodeAttribute) visit(c)).toList();
    String text = ctx.anySequence().getText();

    return new XMLContainerElement(tag, attributes, List.of(new Text(text)));
  }

  @Override
  public NodeAttribute visitSingleQuoteTagAttribute(
      WikiTextParser.SingleQuoteTagAttributeContext ctx) {
    String key = ctx.tagAttributeKeyValues().getText();
    String value =
        ctx.tagAttributeValues().stream()
            .map(v -> (Text) visit(v))
            .map(Text::getContent)
            .reduce("", String::concat);
    return new NodeAttribute(key, value, false);
  }

  @Override
  public NodeAttribute visitDoubleQuoteTagAttribute(
      WikiTextParser.DoubleQuoteTagAttributeContext ctx) {
    String key = ctx.tagAttributeKeyValues().getText();
    String value =
        ctx.tagAttributeValues().stream()
            .map(v -> (Text) visit(v))
            .map(Text::getContent)
            .reduce("", String::concat);
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
    List<WikiTextNode> parameters =
        ctx.templateParameter().stream().map(p -> (WikiTextNode) visit(p)).toList();
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
    return new WikiTextList(
        ListType.UNORDERED,
        ctx.unorderedListItem().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public ListItem visitTerminalUnorderedListItem(
      WikiTextParser.TerminalUnorderedListItemContext ctx) {
    return new ListItem(
        Optional.of(1),
        ctx.sectionContentNoNewline().stream()
            .map(this::visit)
            .map(WikiTextNode.class::cast)
            .toList());
  }

  @Override
  public ListItem visitEnclosingUnorderedListItem(
      WikiTextParser.EnclosingUnorderedListItemContext ctx) {
    ListItem nestedItem = (ListItem) visit(ctx.unorderedListItem());
    return new ListItem(Optional.of(nestedItem.getLevel() + 1), nestedItem.getChildren());
  }

  @Override
  public WikiTextList visitOrderedList(WikiTextParser.OrderedListContext ctx) {
    return new WikiTextList(
        ListType.ORDERED,
        ctx.orderedListItem().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public ListItem visitTerminalOrderedListItem(WikiTextParser.TerminalOrderedListItemContext ctx) {
    return new ListItem(
        Optional.of(1),
        ctx.sectionContentNoNewline().stream()
            .map(this::visit)
            .map(WikiTextNode.class::cast)
            .toList());
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
        ListType.DESCRIPTION,
        Optional.of(title.getContent()),
        ctx.descriptionListItem().stream().map(c -> (WikiTextNode) visit(c)).toList());
  }

  @Override
  public ListItem visitDescriptionListItem(WikiTextParser.DescriptionListItemContext ctx) {
    return new ListItem(
        Optional.empty(),
        ctx.sectionContentNoNewline().stream()
            .map(this::visit)
            .map(WikiTextNode.class::cast)
            .toList());
  }

  @Override
  public WikiTextElement visitBold(WikiTextParser.BoldContext ctx) {
    return new Bold(
        ctx.sectionContent().stream().map(this::visit).map(WikiTextNode.class::cast).toList());
  }

  @Override
  public Italic visitItalics(WikiTextParser.ItalicsContext ctx) {
    return new Italic(
        ctx.sectionContent().stream().map(this::visit).map(WikiTextNode.class::cast).toList());
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
    String display = ctx.text().stream().map(RuleContext::getText).collect(Collectors.joining(" "));
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
    String article = ctx.text().stream().map(RuleContext::getText).collect(Collectors.joining(" "));
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
    return new Text(ctx.text().stream().map(RuleContext::getText).collect(Collectors.joining(" ")));
  }

  @Override
  public UnnamedExternalLink visitUnnamedExternalLink(
      WikiTextParser.UnnamedExternalLinkContext ctx) {
    return new UnnamedExternalLink(ctx.urlCharacters().getText(), true);
  }

  @Override
  public ExternalLink visitNamedExternalLink(WikiTextParser.NamedExternalLinkContext ctx) {
    return new ExternalLink(
        ctx.urlCharacters().getText(),
        true,
        ctx.text().stream().map(RuleContext::getText).collect(Collectors.joining("")));
  }

  @Override
  public HorizontalRule visitHorizontalRule(WikiTextParser.HorizontalRuleContext ctx) {
    return new HorizontalRule();
  }

  @Override
  public Text visitText(WikiTextParser.TextContext ctx) {
    return new Text(convertChildrenToJoinedString(ctx.children));
  }

  @Override
  public Text visitTextUnion(WikiTextParser.TextUnionContext ctx) {
    return new Text(convertChildrenToJoinedString(ctx.children));
  }

  @Override
  public LineBreak visitLineBreak(WikiTextParser.LineBreakContext ctx) {
    return new LineBreak();
  }

  @Override
  public Text visitTerminal(TerminalNode node) {
    return new Text(node.getText());
  }

  private String convertChildrenToJoinedString(List<ParseTree> children) {
    return children.stream()
        .map(c -> (Text) visit(c))
        .map(Text::getContent)
        .reduce("", String::concat);
  }
}
