package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

import com.lucaskjaerozhang.wikitext_parser.Parser;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikiTextLexer;
import com.lucaskjaerozhang.wikitext_parser.grammar.WikitextGrammarBaseTest;
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
class SectionsGrammarTest extends WikitextGrammarBaseTest {

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

    testParseTreeString(
        plainTextString,
        "(root (baseElements (sectionContent (text (textUnion This) (textUnion  ) (textUnion is) (textUnion  ) (textUnion just) (textUnion  ) (textUnion plain) (textUnion  ) (textUnion text)))))");

    Assertions.assertEquals(
        "<article>This is just plain text</article>", Parser.parseToString(plainTextString));
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

    testParseTreeString(
        nestedSectionString,
        "(root (baseElements (sectionLevelOne = (text (textUnion  ) (textUnion Level) (textUnion  ) (textUnion one) (textUnion  )) = (sectionOneContent (sectionContent \\n)) (sectionOneContent (sectionContent (text (textUnion Here) (textUnion  ) (textUnion is) (textUnion  ) (textUnion some) (textUnion  ) (textUnion content)))) (sectionOneContent (sectionContent \\n)) (sectionOneContent (sectionLevelTwo = = (text (textUnion  ) (textUnion Level) (textUnion  ) (textUnion two) (textUnion  )) = = (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent (text (textUnion Here) (textUnion  ) (textUnion is) (textUnion  ) (textUnion some) (textUnion  ) (textUnion level) (textUnion  ) (textUnion two) (textUnion  ) (textUnion content)))) (sectionTwoContent (sectionContent \\n)))) (sectionOneContent (sectionLevelTwo = = (text (textUnion  ) (textUnion Another) (textUnion  ) (textUnion level) (textUnion  ) (textUnion two) (textUnion  )) = = (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent (text (textUnion Here) (textUnion  ) (textUnion is) (textUnion  ) (textUnion more) (textUnion  ) (textUnion level) (textUnion  ) (textUnion two) (textUnion  ) (textUnion content)))) (sectionTwoContent (sectionContent \\n)))))) (baseElements (sectionLevelOne = (text (textUnion  ) (textUnion Level) (textUnion  ) (textUnion one) (textUnion  ) (textUnion again) (textUnion  )) = (sectionOneContent (sectionContent \\n)) (sectionOneContent (sectionContent (text (textUnion More) (textUnion  ) (textUnion content)))))))");

    Assertions.assertEquals(nestedSectionXML, Parser.parseToString(nestedSectionString));
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

    Assertions.assertEquals(nestedSectionXML, Parser.parseToString(nestedSectionString));
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

    testParseTreeString(
        nestedSectionString,
        "(root (baseElements (sectionLevelTwo = = (text (textUnion  ) (textUnion Level) (textUnion  ) (textUnion two) (textUnion  )) = = (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent (text (textUnion Here) (textUnion  ) (textUnion is) (textUnion  ) (textUnion some) (textUnion  ) (textUnion level) (textUnion  ) (textUnion two) (textUnion  ) (textUnion content)))) (sectionTwoContent (sectionContent \\n)))) (baseElements (sectionLevelTwo = = (text (textUnion  ) (textUnion Another) (textUnion  ) (textUnion level) (textUnion  ) (textUnion two) (textUnion  )) = = (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent (text (textUnion Here) (textUnion  ) (textUnion is) (textUnion  ) (textUnion more) (textUnion  ) (textUnion level) (textUnion  ) (textUnion two) (textUnion  ) (textUnion content)))))))");

    Assertions.assertEquals(nestedSectionXML, Parser.parseToString(nestedSectionString));
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

    testParseTreeString(
        stringWithHorizontalRule,
        "(root (baseElements (sectionContent (text (textUnion Some) (textUnion  ) (textUnion text)))) (baseElements (sectionContent \\n)) (baseElements (sectionContent (horizontalRule - - - -))) (baseElements (sectionContent \\n)) (baseElements (sectionContent (text (textUnion More) (textUnion  ) (textUnion text)))))");

    Assertions.assertEquals(horizontalRuleXML, Parser.parseToString(stringWithHorizontalRule));
  }
}
