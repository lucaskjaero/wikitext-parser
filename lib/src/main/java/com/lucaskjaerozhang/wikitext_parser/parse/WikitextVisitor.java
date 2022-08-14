package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.format.Bold;
import com.lucaskjaerozhang.wikitext_parser.objects.format.Italic;
import com.lucaskjaerozhang.wikitext_parser.objects.layout.IndentedBlock;
import com.lucaskjaerozhang.wikitext_parser.objects.layout.XMLBlock;
import com.lucaskjaerozhang.wikitext_parser.objects.list.ListItem;
import com.lucaskjaerozhang.wikitext_parser.objects.list.ListType;
import com.lucaskjaerozhang.wikitext_parser.objects.list.WikiTextList;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.HorizontalRule;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.Text;
import com.lucaskjaerozhang.wikitext_parser.parse.intermediatestate.TagAttribute;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {

  @Override
  public Article visitRoot(WikiTextParser.RootContext ctx) {
    return new Article(ctx.children.stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelOne(WikiTextParser.SectionLevelOneContext ctx) {
    return new Section(
        ctx.text().getText(), 1, ctx.sectionOneContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelTwo(WikiTextParser.SectionLevelTwoContext ctx) {
    return new Section(
        ctx.text().getText(), 2, ctx.sectionTwoContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelThree(WikiTextParser.SectionLevelThreeContext ctx) {
    return new Section(
        ctx.text().getText(), 3, ctx.sectionThreeContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelFour(WikiTextParser.SectionLevelFourContext ctx) {
    return new Section(
        ctx.text().getText(), 4, ctx.sectionFourContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelFive(WikiTextParser.SectionLevelFiveContext ctx) {
    return new Section(
        ctx.text().getText(), 5, ctx.sectionFiveContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelSix(WikiTextParser.SectionLevelSixContext ctx) {
    return new Section(
        ctx.text().getText(), 6, ctx.sectionContent().stream().map(this::visit).toList());
  }

  @Override
  public IndentedBlock visitIndentedBlock(WikiTextParser.IndentedBlockContext ctx) {
    // Indented blocks can be nested in the grammar but we want to unpack them into one level.
    if (ctx.indentedBlock() != null) {
      IndentedBlock innerBlock = (IndentedBlock) visit(ctx.indentedBlock());
      return new IndentedBlock(innerBlock.getLevel() + 1, innerBlock.getContent());
    } else {
      return new IndentedBlock(1, List.of(visit(ctx.text())));
    }
  }

  @Override
  public XMLBlock visitXmlTag(WikiTextParser.XmlTagContext ctx) {
    String tag = ctx.text(0).getText();
    Map<String, String> attributes = buildTagAttributeMap(ctx.tagAttribute());
    List<WikiTextNode> content = ctx.sectionContent().stream().map(this::visit).toList();

    return new XMLBlock(tag, attributes, content);
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public WikiTextNode visitCodeBlock(WikiTextParser.CodeBlockContext ctx) {
    // TODO make this case insensitive
    String tag = "code";
    Map<String, String> attributes = buildTagAttributeMap(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLBlock(tag, attributes, List.of(new Text(text)));
  }

  /*
   * This is a special case of xml tag basically.
   * We don't want to parse the inside of the tag because it could be literally anything.
   * Instead we short circuit parsing early.
   */
  @Override
  public WikiTextNode visitSyntaxHighlightBlock(WikiTextParser.SyntaxHighlightBlockContext ctx) {
    // TODO make this case insensitive
    String tag = "syntaxhighlight";
    Map<String, String> attributes = buildTagAttributeMap(ctx.tagAttribute());
    String text = ctx.anySequence().getText();

    return new XMLBlock(tag, attributes, List.of(new Text(text)));
  }

  @Override
  public TagAttribute visitTagAttribute(WikiTextParser.TagAttributeContext ctx) {
    // There's no repetition of text so we can safely get by index.
    String key = ctx.text().getText();
    String value =
        ctx.tagAttributeValues().stream()
            .map(v -> (Text) visit(v))
            .map(Text::content)
            .reduce("", String::concat);
    return new TagAttribute(key, value);
  }

  @Override
  public WikiTextList visitUnorderedList(WikiTextParser.UnorderedListContext ctx) {
    return new WikiTextList(
        ListType.UNORDERED, ctx.unorderedListItem().stream().map(this::visit).toList());
  }

  @Override
  public ListItem visitTerminalUnorderedListItem(
      WikiTextParser.TerminalUnorderedListItemContext ctx) {
    return new ListItem(Optional.of(1), List.of(visit(ctx.text())));
  }

  @Override
  public ListItem visitEnclosingUnorderedListItem(
      WikiTextParser.EnclosingUnorderedListItemContext ctx) {
    ListItem nestedItem = (ListItem) visit(ctx.unorderedListItem());
    return new ListItem(Optional.of(nestedItem.getLevel() + 1), nestedItem.getContent());
  }

  @Override
  public WikiTextList visitOrderedList(WikiTextParser.OrderedListContext ctx) {
    return new WikiTextList(
        ListType.ORDERED, ctx.orderedListItem().stream().map(this::visit).toList());
  }

  @Override
  public ListItem visitTerminalOrderedListItem(WikiTextParser.TerminalOrderedListItemContext ctx) {
    return new ListItem(Optional.of(1), List.of(visit(ctx.text())));
  }

  @Override
  public ListItem visitEnclosingOrderedListItem(
      WikiTextParser.EnclosingOrderedListItemContext ctx) {
    ListItem nestedItem = (ListItem) visit(ctx.orderedListItem());
    return new ListItem(Optional.of(nestedItem.getLevel() + 1), nestedItem.getContent());
  }

  @Override
  public WikiTextList visitDescriptionList(WikiTextParser.DescriptionListContext ctx) {
    Text title = (Text) visit(ctx.text());
    return new WikiTextList(
        ListType.DESCRIPTION,
        Optional.of(title.content()),
        ctx.descriptionListItem().stream().map(this::visit).toList());
  }

  @Override
  public ListItem visitDescriptionListItem(WikiTextParser.DescriptionListItemContext ctx) {
    return new ListItem(Optional.empty(), List.of(visit(ctx.text())));
  }

  @Override
  public Bold visitBoldText(WikiTextParser.BoldTextContext ctx) {
    return new Bold(List.of(visit(ctx.text())));
  }

  @Override
  public Bold visitBoldItalicText(WikiTextParser.BoldItalicTextContext ctx) {
    return new Bold(List.of(visit(ctx.italics())));
  }

  @Override
  public Italic visitItalics(WikiTextParser.ItalicsContext ctx) {
    return new Italic(List.of(visit(ctx.text())));
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
  public Text visitTerminal(TerminalNode node) {
    return new Text(node.getText());
  }

  private String convertChildrenToJoinedString(List<ParseTree> children) {
    return children.stream()
        .map(c -> (Text) visit(c))
        .map(Text::content)
        .reduce("", String::concat);
  }

  private Map<String, String> buildTagAttributeMap(
      List<WikiTextParser.TagAttributeContext> attributes) {
    return attributes.stream()
        .map(a -> (TagAttribute) visit(a))
        .collect(Collectors.toMap(TagAttribute::key, TagAttribute::value));
  }
}
