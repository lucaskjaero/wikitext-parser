package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.objects.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.temporary.SectionGroup;

import java.util.ArrayList;
import java.util.List;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {

  @Override
  public Article visitRoot(WikiTextParser.RootContext ctx) {
    List<WikiTextNode> content = ctx.sectionContent().stream().map(this::visit).toList();
    SectionGroup subsectionGroup = (SectionGroup) visit(ctx.sectionStart());

    Article article = new Article();
    article.setSections(subsectionGroup.sections());

    return article;
  }

  @Override
  public SectionGroup visitSectionStart(WikiTextParser.SectionStartContext ctx) {
    if (!ctx.sectionLevelOne().isEmpty()) {
      return new SectionGroup(ctx.sectionLevelOne().stream().map(s -> (Section) visit(s)).toList());
    } else if (!ctx.sectionLevelTwo().isEmpty()) {
      return new SectionGroup(ctx.sectionLevelTwo().stream().map(s -> (Section) visit(s)).toList());
    } else if (!ctx.sectionLevelThree().isEmpty()) {
      return new SectionGroup(ctx.sectionLevelThree().stream().map(s -> (Section) visit(s)).toList());
    } else if (!ctx.sectionLevelFour().isEmpty()) {
      return new SectionGroup(ctx.sectionLevelFour().stream().map(s -> (Section) visit(s)).toList());
    } else if (!ctx.sectionLevelFive().isEmpty()) {
      return new SectionGroup(ctx.sectionLevelFive().stream().map(s -> (Section) visit(s)).toList());
    } else if (!ctx.sectionLevelSix().isEmpty()) {
      return new SectionGroup(ctx.sectionLevelSix().stream().map(s -> (Section) visit(s)).toList());
    } else {
      return new SectionGroup(new ArrayList<>());
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
    return new Section(ctx.singleLineValue().TEXT().getText(), 1, sectionContent, subsections);
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
    return new Section(ctx.singleLineValue().TEXT().getText(), 2, sectionContent, subsections);
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
    return new Section(ctx.singleLineValue().TEXT().getText(), 3, sectionContent, subsections);
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
    return new Section(ctx.singleLineValue().TEXT().getText(), 4, sectionContent, subsections);
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
    return new Section(ctx.singleLineValue().TEXT().getText(), 5, sectionContent, subsections);
  }

  @Override
  public Section visitSectionLevelSix(WikiTextParser.SectionLevelSixContext ctx) {
    List<WikiTextNode> sectionContent = ctx.sectionContent().stream().map(this::visit).toList();
    return new Section(ctx.singleLineValue().TEXT().getText(), 6, sectionContent, List.of());
  }

  @Override
  public WikiTextNode visitSectionContent(WikiTextParser.SectionContentContext ctx) {
    return super.visitSectionContent(ctx);
  }
}
