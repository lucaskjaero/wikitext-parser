package com.lucaskjaerozhang.wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.Parser;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class LinkGrammarTest extends WikitextGrammarBaseTest {
  @Test
  void wikiLinksAreCorrectlyIdentified() {
    final String wikiLink = "London has [[public transport]].";
    testLexerTokenTypes(
        wikiLink,
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.OPEN_BRACKET,
            WikiTextLexer.OPEN_BRACKET,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.CLOSE_BRACKET,
            WikiTextLexer.CLOSE_BRACKET,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF));

    testParseTreeString(
        wikiLink,
        "(root (baseElements (sectionContent (text (textUnion London) (textUnion  ) (textUnion has) (textUnion  )))) (baseElements (sectionContent (wikiLink [ [ (wikiLinkTarget (text (textUnion public) (textUnion  ) (textUnion transport))) ] ]))) (baseElements (sectionContent (text (textUnion .)))))");

    Assertions.assertEquals(
        "<article>London has <wikilink target='public transport'>public transport</wikilink>.</article>",
        Parser.parseToString(wikiLink));
  }

  @Test
  void renamedWikiLinksAreCorrectlyIdentified() {
    final String wikiLink = "New York also has [[public transport|public transportation]].";
    testLexerTokenTypes(
        wikiLink,
        Arrays.asList(
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.OPEN_BRACKET,
            WikiTextLexer.OPEN_BRACKET,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.PIPE,
            WikiTextLexer.TEXT,
            WikiTextLexer.SPACE,
            WikiTextLexer.TEXT,
            WikiTextLexer.CLOSE_BRACKET,
            WikiTextLexer.CLOSE_BRACKET,
            WikiTextLexer.TEXT,
            WikiTextLexer.EOF));

    testParseTreeString(
        wikiLink,
        "(root (baseElements (sectionContent (text (textUnion New) (textUnion  ) (textUnion York) (textUnion  ) (textUnion also) (textUnion  ) (textUnion has) (textUnion  )))) (baseElements (sectionContent (wikiLink [ [ (wikiLinkTarget (text (textUnion public) (textUnion  ) (textUnion transport))) | (text (textUnion public) (textUnion  ) (textUnion transportation)) ] ]))) (baseElements (sectionContent (text (textUnion .)))))");

    Assertions.assertEquals(
        "<article>New York also has <wikilink target='public transport'>public transportation</wikilink>.</article>",
        Parser.parseToString(wikiLink));
  }

  @Test
  void allInterwikiLinkComponentsAreCorrectlyIdentified() {
    final String simpleLink = "[[Wiktionary:fr:bonjour#section]]";
    final String renamedLink = "[[Wiktionary:fr:bonjour#section|bonjour]]";
    final String linkXML =
        "<article><wikilink article='bonjour' language='fr' section='section' wiki='Wiktionary'>Wiktionary:fr:bonjour</wikilink></article>";
    final String renamedLinkXML =
        "<article><wikilink article='bonjour' language='fr' section='section' wiki='Wiktionary'>bonjour</wikilink></article>";

    Assertions.assertEquals(linkXML, Parser.parseToString(simpleLink));
    Assertions.assertEquals(linkXML, Parser.parseToString(renamedLink));
  }

  @Test
  void interwikiLinksCorrectlyRename() {
    final String doesNotRename = "[[Wiktionary:fr:bonjour#section|]]";
    final String doesRename = "[[Wiktionary:fr:bonjour|]]";

    final String doesNotRenameXML =
        "<article><wikilink article='bonjour' language='fr' section='section' wiki='Wiktionary'>Wiktionary:fr:bonjour#section</wikilink></article>";
    final String doesRenameXML =
        "<article><wikilink article='bonjour' language='fr' wiki='Wiktionary'>bonjour</wikilink></article>";

    Assertions.assertEquals(doesNotRenameXML, Parser.parseToString(doesNotRename));
    Assertions.assertEquals(doesRenameXML, Parser.parseToString(doesRename));
  }

  @Test
  void redirectsAreCorrectlyHandled() {
    final String redirect = "#REDIRECT [[Wiktionary:fr:bonjour#section]]";

    final String redirectXML =
        "<redirect article='bonjour' language='fr' section='section' wiki='Wiktionary' />";

    Assertions.assertEquals(redirectXML, Parser.parseToString(redirect));
  }

  @Test
  void categoriesAreCorrectlyLinked() {
    final String categorizeWithNoLink = "[[Category:Character sets]]";
    final String linkToCategory = "[[:Category:Character sets]]";
    final String linkToCategoryWithoutPrefix = "[[:Category:Character sets|]]";
  }

  @Test
  void externalLinksAreCorrectlyHandled() {
    final String namedLink = "[https://www.wikipedia.org Wikipedia]";
    final String unnamedLink = "[https://www.wikipedia.org]";
    final String bareURL = "https://www.wikipedia.org";
    final String linkWithoutArrow =
        "<span class=\"plainlinks\">[https://www.wikipedia.org Wikipedia]</span>";
  }
}
