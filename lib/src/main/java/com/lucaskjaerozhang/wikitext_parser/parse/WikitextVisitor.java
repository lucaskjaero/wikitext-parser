package com.lucaskjaerozhang.wikitext_parser.parse;

import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextBaseVisitor;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.layout.Blockquote;
import com.lucaskjaerozhang.wikitext_parser.objects.layout.IndentedBlock;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.Section;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.Text;
import org.antlr.v4.runtime.tree.TerminalNode;

public class WikitextVisitor extends WikiTextBaseVisitor<WikiTextNode> {

  @Override
  public Article visitRoot(WikiTextParser.RootContext ctx) {
    return new Article(ctx.children.stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitBaseElements(WikiTextParser.BaseElementsContext ctx) {
    if (ctx.sectionLevelOne() != null) {
      return visit(ctx.sectionLevelOne());
    } else if (ctx.sectionLevelTwo() != null) {
      return visit(ctx.sectionLevelTwo());
    } else if (ctx.sectionLevelThree() != null) {
      return visit(ctx.sectionLevelThree());
    } else if (ctx.sectionLevelFour() != null) {
      return visit(ctx.sectionLevelFour());
    } else if (ctx.sectionLevelFive() != null) {
      return visit(ctx.sectionLevelFive());
    } else if (ctx.sectionLevelSix() != null) {
      return visit(ctx.sectionLevelSix());
    } else {
      return visit(ctx.sectionContent());
    }
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
  public IndentedBlock visitIndentedBlock(WikiTextParser.IndentedBlockContext ctx) {
    // Indented blocks can be nested in the grammar but we want to unpack them into one level.
    if (ctx.indentedBlock() != null) {
      IndentedBlock innerBlock = (IndentedBlock) visit(ctx.indentedBlock());
      return new IndentedBlock(innerBlock.level() + 1, innerBlock.content());
    } else {
      return new IndentedBlock(1, ctx.TEXT().stream().map(this::visit).toList());
    }
  }

  @Override
  public Blockquote visitBlockQuote(WikiTextParser.BlockQuoteContext ctx) {
    return new Blockquote(ctx.sectionContent().stream().map(this::visit).toList());
  }

  @Override
  public WikiTextNode visitTerminal(TerminalNode node) {
    return new Text(node.getText());
  }
}
