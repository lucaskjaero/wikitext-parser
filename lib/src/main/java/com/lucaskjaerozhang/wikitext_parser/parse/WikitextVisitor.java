package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.objects.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.TextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {

  @Override
  public Article visitRoot(WikiTextParser.RootContext ctx) {
    if (!ctx.sectionLevelOne().isEmpty()) {
      return new Article(ctx.sectionLevelOne().stream().map(this::visit).toList());
    } else if (!ctx.sectionLevelTwo().isEmpty()) {
      return new Article(ctx.sectionLevelTwo().stream().map(this::visit).toList());
    } else if (!ctx.sectionLevelThree().isEmpty()) {
      return new Article(ctx.sectionLevelThree().stream().map(this::visit).toList());
    } else if (!ctx.sectionLevelFour().isEmpty()) {
      return new Article(ctx.sectionLevelFour().stream().map(this::visit).toList());
    } else if (!ctx.sectionLevelFive().isEmpty()) {
      return new Article(ctx.sectionLevelFive().stream().map(this::visit).toList());
    } else if (!ctx.sectionLevelSix().isEmpty()) {
      return new Article(ctx.sectionLevelSix().stream().map(this::visit).toList());
    } else {
      return new Article(ctx.sectionContent().stream().map(this::visit).toList());
    }
  }

  @Override
  public Section visitSectionLevelOne(WikiTextParser.SectionLevelOneContext ctx) {
    List<WikiTextParser.SectionOneContentContext> contentNodes = ctx.sectionOneContent();
    List<WikiTextNode> content = contentNodes.stream().map(this::visit).toList();
    List<Section> subsections =
        content.stream()
            .filter(c -> c.getType().equals(Section.TYPE))
            .map(Section.class::cast)
            .toList();
    List<WikiTextNode> sectionContent =
        content.stream().filter(c -> !c.getType().equals(Section.TYPE)).toList();
    return new Section(ctx.TEXT().getText(), 1, sectionContent, subsections);
  }

  @Override
  public Section visitSectionLevelTwo(WikiTextParser.SectionLevelTwoContext ctx) {
    List<WikiTextNode> content = ctx.sectionTwoContent().stream().map(this::visit).toList();
    List<Section> subsections =
        content.stream()
            .filter(c -> c.getType().equals(Section.TYPE))
            .map(Section.class::cast)
            .toList();
    List<WikiTextNode> sectionContent =
        content.stream().filter(c -> !c.getType().equals(Section.TYPE)).toList();
    return new Section(ctx.TEXT().getText(), 2, sectionContent, subsections);
  }

  @Override
  public Section visitSectionLevelThree(WikiTextParser.SectionLevelThreeContext ctx) {
    List<WikiTextNode> content = ctx.sectionThreeContent().stream().map(this::visit).toList();
    List<Section> subsections =
        content.stream()
            .filter(c -> c.getType().equals(Section.TYPE))
            .map(Section.class::cast)
            .toList();
    List<WikiTextNode> sectionContent =
        content.stream().filter(c -> !c.getType().equals(Section.TYPE)).toList();
    return new Section(ctx.TEXT().getText(), 3, sectionContent, subsections);
  }

  @Override
  public Section visitSectionLevelFour(WikiTextParser.SectionLevelFourContext ctx) {
    List<WikiTextNode> content = ctx.sectionFourContent().stream().map(this::visit).toList();
    List<Section> subsections =
        content.stream()
            .filter(c -> c.getType().equals(Section.TYPE))
            .map(Section.class::cast)
            .toList();
    List<WikiTextNode> sectionContent =
        content.stream().filter(c -> !c.getType().equals(Section.TYPE)).toList();
    return new Section(ctx.TEXT().getText(), 4, sectionContent, subsections);
  }

  @Override
  public Section visitSectionLevelFive(WikiTextParser.SectionLevelFiveContext ctx) {
    List<WikiTextNode> content = ctx.sectionFiveContent().stream().map(this::visit).toList();
    List<Section> subsections =
        content.stream()
            .filter(c -> c.getType().equals(Section.TYPE))
            .map(Section.class::cast)
            .toList();
    List<WikiTextNode> sectionContent =
        content.stream().filter(c -> !c.getType().equals(Section.TYPE)).toList();
    return new Section(ctx.TEXT().getText(), 5, sectionContent, subsections);
  }

  @Override
  public Section visitSectionLevelSix(WikiTextParser.SectionLevelSixContext ctx) {
    List<WikiTextNode> sectionContent = ctx.sectionContent().stream().map(this::visit).toList();
    return new Section(ctx.TEXT().getText(), 6, sectionContent, List.of());
  }

  @Override
  public WikiTextNode visitSectionContent(WikiTextParser.SectionContentContext ctx) {
    if (ctx.indentedBlock() != null) {
      return visit(ctx.indentedBlock());
    } else if (ctx.blockQuote() != null) {
      return visit(ctx.blockQuote());
    } else if (ctx.HORIZONTAL_RULE() != null) {
      return visit(ctx.HORIZONTAL_RULE());
    } else if (ctx.LINE_BREAK() != null) {
      return visit(ctx.LINE_BREAK());
    } else if (ctx.NEWLINE() != null) {
      return visit(ctx.NEWLINE());
    } else {
      return visit(ctx.TEXT());
    }
  }

  @Override
  public WikiTextNode visitSectionOneContent(WikiTextParser.SectionOneContentContext ctx) {
    if (ctx.sectionLevelTwo() != null) {
      return visit(ctx.sectionLevelTwo());
    } else {
      return visit(ctx.sectionContent());
    }
  }

  @Override
  public WikiTextNode visitSectionTwoContent(WikiTextParser.SectionTwoContentContext ctx) {
    if (ctx.sectionLevelThree() != null) {
      return visit(ctx.sectionLevelThree());
    } else {
      return visit(ctx.sectionContent());
    }
  }

  @Override
  public WikiTextNode visitSectionThreeContent(WikiTextParser.SectionThreeContentContext ctx) {
    if (ctx.sectionLevelFour() != null) {
      return visit(ctx.sectionLevelFour());
    } else {
      return visit(ctx.sectionContent());
    }
  }

  @Override
  public WikiTextNode visitSectionFourContent(WikiTextParser.SectionFourContentContext ctx) {
    if (ctx.sectionLevelFive() != null) {
      return visit(ctx.sectionLevelFive());
    } else {
      return visit(ctx.sectionContent());
    }
  }

  @Override
  public WikiTextNode visitSectionFiveContent(WikiTextParser.SectionFiveContentContext ctx) {
    if (ctx.sectionLevelSix() != null) {
      return visit(ctx.sectionLevelSix());
    } else {
      return visit(ctx.sectionContent());
    }
  }

  @Override
  public WikiTextNode visitIndentedBlock(WikiTextParser.IndentedBlockContext ctx) {
    return super.visitIndentedBlock(ctx);
  }

  @Override
  public WikiTextNode visitBlockQuote(WikiTextParser.BlockQuoteContext ctx) {
    return super.visitBlockQuote(ctx);
  }

  @Override
  public WikiTextNode visitTerminal(TerminalNode node) {
    return new TextNode(node.getText());
  }
}
