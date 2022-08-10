package com.lucaskjaerozhang.wikitext_parser.grammar.layout;

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
        plainTextString, "(root (sectionContent (singleLineValue This is just plain text)))");
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

    testParseTreeString(
        nestedSectionString,
        "(root (sectionStart (sectionLevelOne = (singleLineValue  Level one ) = (sectionOneContent (sectionContent \\n) (sectionContent (singleLineValue Here is some content)) (sectionContent \\n)) (sectionOneContent (sectionLevelTwo == (singleLineValue  Level two ) == (sectionTwoContent (sectionContent \\n) (sectionContent (singleLineValue Here is some level two content)) (sectionContent \\n))) (sectionLevelTwo == (singleLineValue  Another level two ) == (sectionTwoContent (sectionContent \\n) (sectionContent (singleLineValue Here is more level two content)) (sectionContent \\n))))) (sectionLevelOne = (singleLineValue  Level one again ) = (sectionOneContent (sectionContent \\n) (sectionContent (singleLineValue More content))))))");
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

    testParseTreeString(
        nestedSectionString,
        "(root (sectionStart (sectionLevelTwo == (singleLineValue  Level two ) == (sectionTwoContent (sectionContent \\n) (sectionContent (singleLineValue Here is some level two content)) (sectionContent \\n))) (sectionLevelTwo == (singleLineValue  Another level two ) == (sectionTwoContent (sectionContent \\n) (sectionContent (singleLineValue Here is more level two content))))))");
  }

  @Test
  void horizontalRulesAreRecognized() {
    final String stringWithHorizontalRule = "Some text\n----\nMore text";
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
        "(root (sectionContent (singleLineValue Some text)) (sectionContent \\n) (sectionContent ----) (sectionContent \\n) (sectionContent (singleLineValue More text)))");
  }
}
