package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.layout.IndentedBlock;
import com.lucaskjaerozhang.wikitext_parser.objects.layout.XMLBlock;
import com.lucaskjaerozhang.wikitext_parser.objects.list.ListItem;
import com.lucaskjaerozhang.wikitext_parser.objects.list.ListType;
import com.lucaskjaerozhang.wikitext_parser.objects.list.WikiTextList;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.HorizontalRule;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.Text;
import com.lucaskjaerozhang.wikitext_parser.parse.intermediatestate.OpenTag;
import com.lucaskjaerozhang.wikitext_parser.parse.intermediatestate.TagAttribute;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.TerminalNode;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {

  @Override
  public Article visitRoot(WikiTextParser.RootContext ctx) {
    return new Article(ctx.children.stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelOne(WikiTextParser.SectionLevelOneContext ctx) {
    return new Section(
        ctx.TEXT().getText(), 1, ctx.sectionOneContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelTwo(WikiTextParser.SectionLevelTwoContext ctx) {
    return new Section(
        ctx.TEXT().getText(), 2, ctx.sectionTwoContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelThree(WikiTextParser.SectionLevelThreeContext ctx) {
    return new Section(
        ctx.TEXT().getText(), 3, ctx.sectionThreeContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelFour(WikiTextParser.SectionLevelFourContext ctx) {
    return new Section(
        ctx.TEXT().getText(), 4, ctx.sectionFourContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelFive(WikiTextParser.SectionLevelFiveContext ctx) {
    return new Section(
        ctx.TEXT().getText(), 5, ctx.sectionFiveContent().stream().map(this::visit).toList());
  }

  @Override
  public Section visitSectionLevelSix(WikiTextParser.SectionLevelSixContext ctx) {
    return new Section(
        ctx.TEXT().getText(), 6, ctx.sectionContent().stream().map(this::visit).toList());
  }

  @Override
  public IndentedBlock visitIndentedBlock(WikiTextParser.IndentedBlockContext ctx) {
    // Indented blocks can be nested in the grammar but we want to unpack them into one level.
    if (ctx.indentedBlock() != null) {
      IndentedBlock innerBlock = (IndentedBlock) visit(ctx.indentedBlock());
      return new IndentedBlock(innerBlock.getLevel() + 1, innerBlock.getContent());
    } else {
      return new IndentedBlock(1, ctx.TEXT().stream().map(this::visit).toList());
    }
  }

  @Override
  public WikiTextNode visitXmlTag(WikiTextParser.XmlTagContext ctx) {
    OpenTag tag = (OpenTag) visit(ctx.openTag());
    return new XMLBlock(
        tag.tag(), tag.attributes(), ctx.sectionContent().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitOpenTag(WikiTextParser.OpenTagContext ctx) {
    Map<String, String> attributes =
        ctx.tagAttribute().stream()
            .map(a -> (TagAttribute) visit(a))
            .collect(Collectors.toMap(TagAttribute::key, TagAttribute::value));
    return new OpenTag(ctx.TEXT().getText(), attributes);
  }

  @Override
  public TagAttribute visitTagAttribute(WikiTextParser.TagAttributeContext ctx) {
    // There's no repetition of text so we can safely get by index.
    String key = ctx.TEXT(0).getText();
    String value = ctx.TEXT(1).getText();
    return new TagAttribute(key, value);
  }

  @Override
  public WikiTextNode visitUnorderedList(WikiTextParser.UnorderedListContext ctx) {
    return new WikiTextList(
        ListType.UNORDERED,
        Optional.empty(),
        ctx.unorderedListItem().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitUnorderedListItem(WikiTextParser.UnorderedListItemContext ctx) {
    return new ListItem(ctx.TEXT().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitOrderedList(WikiTextParser.OrderedListContext ctx) {
    return new WikiTextList(
        ListType.ORDERED,
        Optional.empty(),
        ctx.orderedListItem().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitOrderedListItem(WikiTextParser.OrderedListItemContext ctx) {
    return new ListItem(ctx.TEXT().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitDescriptionList(WikiTextParser.DescriptionListContext ctx) {
    return new WikiTextList(
        ListType.DESCRIPTION,
        Optional.of(
            ctx.TEXT().stream()
                .map(this::visit)
                .map(WikiTextNode::toXML)
                .reduce("", String::concat)),
        ctx.descriptionListItem().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitDescriptionListItem(WikiTextParser.DescriptionListItemContext ctx) {
    return new ListItem(ctx.TEXT().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitTerminal(TerminalNode node) {
    return switch (node.getSymbol().getType()) {
      case WikiTextParser.HORIZONTAL_RULE -> new HorizontalRule();
      default -> new Text(node.getText());
    };
  }
}
