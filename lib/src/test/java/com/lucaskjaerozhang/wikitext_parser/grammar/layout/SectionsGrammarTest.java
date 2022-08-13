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
    final List<Integer> tokenTypes = Arrays.asList(WikiTextLexer.TEXT, WikiTextLexer.EOF);

    testLexerTokenTypes(plainTextString, tokenTypes);

    Assertions.assertEquals(1, getResultsFromXPATH(plainTextString, "//TEXT").size());

    testParseTreeString(
        plainTextString, "(root (baseElements (sectionContent This is just plain text)))");

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
        "(root (baseElements (sectionLevelOne =  Level one  = (sectionOneContent (sectionContent \\n)) (sectionOneContent (sectionContent Here is some content)) (sectionOneContent (sectionContent \\n)) (sectionOneContent (sectionLevelTwo ==  Level two  == (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent Here is some level two content)) (sectionTwoContent (sectionContent \\n)))) (sectionOneContent (sectionLevelTwo ==  Another level two  == (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent Here is more level two content)) (sectionTwoContent (sectionContent \\n)))))) (baseElements (sectionLevelOne =  Level one again  = (sectionOneContent (sectionContent \\n)) (sectionOneContent (sectionContent More content)))))");

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
        "(root (baseElements (sectionLevelTwo ==  Level two  == (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent Here is some level two content)) (sectionTwoContent (sectionContent \\n)))) (baseElements (sectionLevelTwo ==  Another level two  == (sectionTwoContent (sectionContent \\n)) (sectionTwoContent (sectionContent Here is more level two content)))))");

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
            WikiTextLexer.NEWLINE,
            WikiTextLexer.HORIZONTAL_RULE,
            WikiTextLexer.NEWLINE,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF));

    Assertions.assertEquals(2, getResultsFromXPATH(stringWithHorizontalRule, "//TEXT").size());
    Assertions.assertEquals(
        1, getResultsFromXPATH(stringWithHorizontalRule, "//HORIZONTAL_RULE").size());

    testParseTreeString(
        stringWithHorizontalRule,
        "(root (baseElements (sectionContent Some text)) (baseElements (sectionContent \\n)) (baseElements (sectionContent ----)) (baseElements (sectionContent \\n)) (baseElements (sectionContent More text)))");

    Assertions.assertEquals(horizontalRuleXML, Parser.parseToString(stringWithHorizontalRule));
  }
}
