package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.objects.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.List;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {

  @Override
  public Article visitRoot(WikiTextParser.RootContext ctx) {
    List<Section> sections =
        ctx.sectionContent().stream().map(node -> (Section) visit(node)).toList();

    Article article = new Article();
    article.setSections(sections);

    return article;
  }

  @Override
  public Section visitSectionLevelOne(WikiTextParser.SectionLevelOneContext ctx) {
    List<WikiTextNode> content = ctx.sectionOneContent().stream().map(this::visit).toList();
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
