package com.lucaskjaerozhang.wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.grammar.parse.WikiTextLexer;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class LinkGrammarTest extends WikitextBaseTest {
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

    testTranslation(
        wikiLink,
        "<article>London has <wikilink article='public transport'>public transport</wikilink>.</article>");
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

    testTranslation(
        wikiLink,
        "<article>New York also has <wikilink article='public transport'>public transportation</wikilink>.</article>");
  }

  @Test
  void allInterwikiLinkComponentsAreCorrectlyIdentified() {
    final String simpleLink = "[[Wiktionary:fr:bonjour#section]]";
    final String renamedLink = "[[Wiktionary:fr:bonjour#section|bonjour]]";
    final String linkXML =
        "<article><wikilink article='bonjour' language='fr' section='section' wiki='Wiktionary'>Wiktionary:fr:bonjour#section</wikilink></article>";
    final String renamedLinkXML =
        "<article><wikilink article='bonjour' language='fr' section='section' wiki='Wiktionary'>bonjour</wikilink></article>";

    testTranslation(simpleLink, linkXML);
    testTranslation(renamedLink, renamedLinkXML);
  }

  @Test
  void interwikiLinksCorrectlyRename() {
    final String doesNotRename = "[[Wiktionary:fr:bonjour#section|]]";
    final String doesRename = "[[Wiktionary:fr:bonjour (hi), bye|]]";

    final String doesNotRenameXML =
        "<article><wikilink article='bonjour' language='fr' section='section' wiki='Wiktionary'>Wiktionary:fr:bonjour#section</wikilink></article>";
    final String doesRenameXML =
        "<article><wikilink article='bonjour (hi), bye' language='fr' wiki='Wiktionary'>bonjour</wikilink></article>";

    testTranslation(doesNotRename, doesNotRenameXML);
    testTranslation(doesRename, doesRenameXML);
  }

  @Test
  void redirectsAreCorrectlyHandled() {
    final String redirect = "#REDIRECT [[Wiktionary:fr:bonjour#section]]";

    final String redirectXML =
        "<redirect article='bonjour' language='fr' section='section' wiki='Wiktionary' />";

    testTranslation(redirect, redirectXML);
  }

  @Test
  void categoriesAreCorrectlyLinked() {
    final String categoryWithNoLink = "Article content [[Category:Character sets]]";
    final String categoryWithNoLinkXML =
        "<article><categories><category>Category:Character sets</category></categories>Article content </article>";
    testTranslation(categoryWithNoLink, categoryWithNoLinkXML);

    final String linkToCategory = "Article content [[:Category:Character sets]]";
    final String linkToCategoryXML =
        "<article><categories><category>Category:Character sets</category></categories>Article content <category article='Character sets'>Category:Character sets</category></article>";
    testTranslation(linkToCategory, linkToCategoryXML);

    final String linkToCategoryWithoutPrefix = "Article content [[:Category:Character sets|]]";
    final String linkToCategoryWithoutPrefixXML =
        "<article><categories><category>Category:Character sets</category></categories>Article content <category article='Character sets'>Character sets</category></article>";
    testTranslation(linkToCategoryWithoutPrefix, linkToCategoryWithoutPrefixXML);
  }

  @Test
  void externalLinksAreCorrectlyHandled() {
    final String namedLink = "[https://www.wikipedia.org Wikipedia]";
    final String namedLinkXML =
        "<article><link arrow='true' href='https://www.wikipedia.org'>Wikipedia</link></article>";
    testTranslation(namedLink, namedLinkXML);

    final String unnamedLink = "[https://www.wikipedia.org]";
    final String unnamedLinkXML =
        "<article><link arrow='true' href='https://www.wikipedia.org' /></article>";
    testTranslation(unnamedLink, unnamedLinkXML);

    // TODO need to write a grammar recognizing URLs
    //    final String bareURL = "https://www.wikipedia.org";
    //    final String bareURLXML =
    //        "<article><link arrow='true'
    // href='https://www.wikipedia.org'>https://www.wikipedia.org</a></article>";
    //    Assertions.assertEquals(bareURLXML, Parser.parseToString(bareURL));

    final String bareURLNoWiki = "<nowiki>https://www.wikipedia.org</nowiki>";
    final String bareURLNoWikiXML = "<article><nowiki>https://www.wikipedia.org</nowiki></article>";
    testTranslation(bareURLNoWiki, bareURLNoWikiXML);

    final String bareURLNoWikiUppercase = "<NOWIKI>https://www.wikipedia.org</NOWIKI>";
    final String bareURLNoWikiUppercaseXML =
        "<article><NOWIKI>https://www.wikipedia.org</NOWIKI></article>";
    testTranslation(bareURLNoWikiUppercase, bareURLNoWikiUppercaseXML);

    final String linkWithoutArrow =
        "<span class=\"plainlinks\">[https://www.wikipedia.org Wikipedia]</span>";
    final String linkWithoutArrowXML =
        "<article><span class=\"plainlinks\"><link arrow='false' href='https://www.wikipedia.org'>Wikipedia</link></span></article>";
    testTranslation(linkWithoutArrow, linkWithoutArrowXML);
  }
}
