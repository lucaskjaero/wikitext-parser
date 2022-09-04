package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.common.client.FileCachingWikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.OnlineTemplateProvider;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PreprocessorEndToEndTest {
  private final WikiClient testClient =
      FileCachingWikiClient.builder()
          .cacheDirectory("src/test/resources")
          .sourceClient(WikiRestClient.builder().build())
          .build();
  private final TemplateProvider templateProvider = new OnlineTemplateProvider(testClient);

  public void testPreprocessor(String expected, String input) {
    Preprocessor preprocessor =
        new Preprocessor(
            new PreprocessorVariables(
                Map.of(
                    "PAGENAME", "Moratorium", "NAMESPACE", "Template", "NAMESPACEE", "Template")),
            templateProvider);
    String actual = preprocessor.preprocess(input, true);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void moratoriumTest() {
    String input =
        testClient
            .getPageSource("Moratorium_(law)")
            .orElseThrow(() -> new IllegalStateException("Failed to get moratorium"))
            .getSource();

    String expected =
        """
            {{#ifeq:safesubst:|exclude||[[Category:safesubst: with short description]]}}
            {{SAFESUBST:<noinclude />#invoke:Unsubst||date= |$B=
            {{Ambox
            | name  = More citations needed
            | small =\040
            | type  = content
            | class = ambox-Refimprove
            | image = [[File:Question book-new.svg|50x40px|alt=]]
            | issue = This article '''needs additional citations for [[Wikipedia:Verifiability|verification]]'''.
            | fix   = Please help [{{fullurl:{{FULLPAGENAME:{{{1}}}}}[[Category:Pages which use a template in place of a magic word|DFULLPAGENAME]]|action=edit}} improve this article] by [[Help:Referencing for beginners|adding citations to reliable sources]]. Unsourced material may be challenged and removed.{{#if:{{{unquoted|}}}| <br /><small>{{find sources mainspace|{{#if:|{{{find}}}|.}}|{{{unquoted|}}}}}</small> |{{#if:|{{#ifeq:  |none ||<br /><small>{{find sources mainspace|{{{find}}} }}</small>}}|<br /><small>{{#invoke:Find sources|Find sources mainspace}}</small>}} }}
            | removalnotice = yes
            | talk  =\s
            | date  = April 2009
            | cat   = Articles needing additional references
            | all   = All articles needing additional references
            }}
            }}
            A '''moratorium''' is a delay or suspension of an activity or a law. In a [[legal]] context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.

            For example, [[animal rights]] activists and [[Conservation movement|conservation]] authorities may request fishing or hunting moratoria to protect [[endangered]] or threatened animal species.  These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.

            Another instance is a delay of legal obligations or payment (''[[debt moratorium]]''). A legal official can order {{ safesubst:<noinclude/>#invoke:Unsubst||date= |$B=
            {{Fix-span
            |link=Wikipedia:Please clarify
            |text=clarification needed
            |title=safesubst:
            |date=December 2015
            |pre-text=
            |post-text=
            |cat-date=Category:Wikipedia articles needing clarification
            |content=a delay of payment
            }}
            }} due to extenuating circumstances, which render one party incapable of paying another.<ref>{{#invoke:citation/CS1|citation
            |CitationClass=web
            }}</ref>

            ==See also==
            {{Sister project
            |position=
            |project=wiktionary
            |text=Look up '''''[[wiktionary:Special:Search/{{lc:safesubst:}}|{{lc:safesubst:}}]]'''''{{#if:
              |{{#if:
               |,
               |&nbsp;or
              }} '''''[[wiktionary:{{{2}}}|{{{2}}}]]'''''
             }}{{#if:
              |{{#if:
               |,
               |, or
              }} '''''[[wiktionary:{{{3}}}|{{{3}}}]]'''''
             }}{{#if:
              |{{#if:
               |,
               |, or
              }} '''''[[wiktionary:{{{4}}}|{{{4}}}]]'''''
             }}{{#if:
              |{{#if:
               |,
               |, or
              }} '''''[[wiktionary:{{{5}}}|{{{5}}}]]'''''
             }}{{#if:
              |{{#if:
               |,
               |, or
              }} '''''[[wiktionary:{{{6}}}|{{{6}}}]]'''''
             }}{{#if:
              |{{#if:
               |,
               |, or
              }} '''''[[wiktionary:{{{7}}}|{{{7}}}]]'''''
             }}{{#if:
              |{{#if:
               |,
               |, or
              }} '''''[[wiktionary:{{{8}}}|{{{8}}}]]'''''
             }}{{#if:
              |{{#if:
               |,
               |, or
              }} '''''[[wiktionary:{{{9}}}|{{{9}}}]]'''''
             }}{{#if:
              |, or '''''[[wiktionary:{{{10}}}|{{{10}}}]]'''''
             }} in Wiktionary, the free dictionary.
            }}
            *[[Justice delayed is justice denied]]
            *[[2010 U.S. Deepwater Drilling Moratorium]]
            *[[Moratorium to End the War in Vietnam]]
            *[[UN moratorium on the death penalty]]

            ==References==
            <templatestyles src="Reflist/styles.css" /><div class="reflist <!--
            -->{{#if:{{{1|}}}{{{colwidth|}}}|reflist-columns references-column-width}} <!--
            -->{{#switch:{{{liststyle|{{{group|}}}}}}|upper-alpha|upper-roman|lower-alpha|lower-greek|lower-roman=reflist-{{{liststyle|{{{group}}}}}}}} <!--
            -->{{#if:{{{1|}}}|{{#iferror:{{#ifexpr: {{{1|1}}} > 1 }}||{{#switch:{{{1|}}}|1=|2=reflist-columns-2|#default=reflist-columns-3}} }}}}" <!-- end class
            -->{{#if: {{{1|}}}<!-- start style -->
                | {{#iferror: {{#ifexpr: {{{1|1}}} > 1 }} |style="column-width: {{{1}}};"}}
                | {{#if: {{{colwidth|}}}|style="column-width: {{{colwidth}}};"}}
                }}>
            {{#tag:references|{{{refs|}}}|group={{{group|}}}|responsive={{#if:{{{1|}}}{{{colwidth|}}}|0|1}}}}</div>{{#invoke:Check for unknown parameters|check|unknown={{main other|[[Category:Pages using reflist with unknown parameters|_VALUE_Reflist]]}}|preview=Page using [[Template:Reflist]] with unknown parameter "_VALUE_"|ignoreblank=y| 1 | colwidth | group | liststyle | refs }}
            *{{#ifeq: Template |<!--is set-->
                 |<!--Sort out the parameters
                 -->[[Category:Wikipedia articles incorporating a citation from the New International Encyclopedia]]<!--
                 -->{{#if:|[[Category:Wikipedia articles incorporating text via vb from the New International Encyclopedia]]}}<!--
                 -->{{#if:|[[Category:Wikipedia articles incorporating a citation from the New International Encyclopedia with an unnamed parameter]]}}<!--
                 -->{{#if:Moratorium||{{#switch:
              <!--If no or empty "demospace" parameter then detect namespace-->
              {{#if:\s
              | {{lc: {{{demospace}}} }}    <!--Use lower case "demospace"-->
              | {{#ifeq:Template|{{ns:0}}
                | main
                | other
                }}\s
              }}
            | main     = [[Category:Cite NIE template missing title parameter]]
            | other
            | #default =\s
            }}}}<!--
            end {NAMESPACEE-->}}

            <noinclude>
            </noinclude>{{#switch:Template||{{ns:14}}=<noinclude> Only articles and categories.\s
            </noinclude>{{#invoke:Check for unknown parameters|check|arts|state|extralist|ignoreblank=1|showblankpositional=1|unknown=[[Category:Pages using authority control with parameters|_VALUE_]]|preview=Page using [[Template:Authority control]] with "_VALUE_", please move this to Wikidata if possible}}
            }}

            [[Category:Legal terminology]]


            {{asbox
            | image     = Scale of justice 2.svg
            | pix       = 22
            | subject   = [[law|legal term]]\s
            | qualifier =\s
            | category  = Legal terminology stubs
            | tempsort  =\s
            | name      = Template:Law-term-stub
            }}""";

    testPreprocessor(expected, input);
  }

  @Test
  void jupiterTest() {
    String input =
        testClient
            .getPageSource("Jupiter")
            .orElseThrow(() -> new IllegalStateException("Failed to get jupiter"))
            .getSource();

    String expected = """
                """;

    testPreprocessor(expected, input);
  }
}
