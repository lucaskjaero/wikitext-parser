package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

import com.lucaskjaerozhang.wikitext_parser.TestErrorListener;
import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.root.Article;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import com.lucaskjaerozhang.wikitext_parser.grammar.parse.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.BaseTemplateProvider;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Sections
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class SectionsGrammarTest extends WikitextBaseTest {

  @Test
  void plainTextIsRecognized() {
    final String plainTextString = "This is just plain text";
    final List<Integer> tokenTypes =
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF);

    testLexerTokenTypes(plainTextString, tokenTypes);

    Assertions.assertEquals(1, getResultsFromXPATH(plainTextString, "//text").size());

    Article root =
        (Article)
            ParseTreeBuilder.visitTreeFromText(
                plainTextString,
                new BaseTemplateProvider(),
                List.of(new TestErrorListener()),
                true);
    Assertions.assertEquals(
        "<article>This is just plain text</article>",
        com.lucaskjaerozhang.wikitext_parser.WikiTextParser.writeToString(root));

    List<WikiTextNode> children = root.getChildren();
    Assertions.assertEquals(2, children.size());
    Text textNode = (Text) children.get(1);
    Assertions.assertEquals("This is just plain text", textNode.getContent());
  }

  @Test
  void nonEnglishPlainTextIsRecognized() {
    final String plainTextString = "這不是英文，程序失敗了嗎？";

    testTranslation(plainTextString, "<article>這不是英文，程序失敗了嗎？</article>");
  }

  /*
   * Pulling content out of a section is a super common workflow, so this needs to be easy.
   */
  @Test
  void nestedSectionsAreRecognized() {
    final String nestedSectionString =
        """
            = Level one =
            Here is some content
            == Level two ==
            Here is some level two content
            == Another level two ==
            Here is more level two content
            = Level one again =
            More content""";

    final String nestedSectionXML =
        """
            <article><section level='1' title='Level one'>
            Here is some content
            <section level='2' title='Level two'>
            Here is some level two content
            </section><section level='2' title='Another level two'>
            Here is more level two content
            </section></section><section level='1' title='Level one again'>
            More content</section></article>""";

    testTranslation(nestedSectionString, nestedSectionXML);
  }

  /*
   * Pulling content out of a section is a super common workflow, so this needs to be easy.
   */
  @Test
  void nestedSectionsCanSkipLevels() {
    final String nestedSectionString =
        """
                = One =
                1
                === Three ===
                3
                ===== Five =====
                5
                ====== Six ======
                6
                """;

    final String nestedSectionXML =
        """
                <article><section level='1' title='One'>
                1
                <section level='3' title='Three'>
                3
                <section level='5' title='Five'>
                5
                <section level='6' title='Six'>
                6
                </section></section></section></section></article>""";

    testTranslation(nestedSectionString, nestedSectionXML);
  }

  /** This matters because many wikis start at section level 2 for everything. */
  @Test
  void sectionsCanStartAtAnyLevel() {
    final String nestedSectionString =
        """
                == Level two ==
                Here is some level two content
                == Another level two ==
                Here is more level two content""";
    final String nestedSectionXML =
        """
            <article><section level='2' title='Level two'>
            Here is some level two content
            </section><section level='2' title='Another level two'>
            Here is more level two content</section></article>""";

    testTranslation(nestedSectionString, nestedSectionXML);
  }

  @Test
  void horizontalRulesAreRecognized() {
    final String stringWithHorizontalRule = "Some text\n----\nMore text";
    final String horizontalRuleXML = "<article>Some text\n<horizontalRule />\nMore text</article>";

    testLexerTokenTypes(
        stringWithHorizontalRule,
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.DASH,
            WikiTextLexer.DASH,
            WikiTextLexer.DASH,
            WikiTextLexer.DASH,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF));

    Assertions.assertEquals(2, getResultsFromXPATH(stringWithHorizontalRule, "//text").size());
    Assertions.assertEquals(
        1, getResultsFromXPATH(stringWithHorizontalRule, "//horizontalRule").size());

    testTranslation(stringWithHorizontalRule, horizontalRuleXML);
  }
}
