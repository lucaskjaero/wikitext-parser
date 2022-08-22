package com.lucaskjaerozhang.wikitext_parser.e2e;

import com.lucaskjaerozhang.wikitext_parser.TestErrorListener;
import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WikipediaTest extends WikitextBaseTest {

  @Test
  void moratoriumIsCorrectlyParsed() {
    String article =
        """
                {{Short description|Delay or suspension of an activity or a law}}
                {{more citations needed|date=April 2009}}
                A '''moratorium''' is a delay or suspension of an activity or a law. In a [[legal]] context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.

                For example, [[animal rights]] activists and [[Conservation movement|conservation]] authorities may request fishing or hunting moratoria to protect [[endangered]] or threatened animal species.  These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.

                Another instance is a delay of legal obligations or payment (''[[debt moratorium]]''). A legal official can order {{clarify|text=a delay of payment|date=December 2015}} due to extenuating circumstances, which render one party incapable of paying another.<ref>{{cite web|url=http://dictionary.reference.com/browse/moratorium?s=t|title=definition of moratorium|author=dictionary.com|work=dictionary.com}}</ref>

                ==See also==
                {{wiktionary|moratorium}}
                *[[Justice delayed is justice denied]]
                *[[2010 U.S. Deepwater Drilling Moratorium]]
                *[[Moratorium to End the War in Vietnam]]
                *[[UN moratorium on the death penalty]]

                ==References==
                {{Reflist}}
                *{{cite NIE|wstitle=Moratorium|year=1905}}

                {{Authority control}}

                [[Category:Legal terminology]]


                {{Law-term-stub}}

                """;
    String xml =
        """
            <article><categories><category>Category:Legal terminology</category></categories><template name='Short description'><parameter value='Delay or suspension of an activity or a law' /></template>
            <template name='more citations needed'><parameter key='date' value='April 2009' /></template>
            A <bold>moratorium</bold> is a delay or suspension of an activity or a law. In a <wikilink article='legal'>legal</wikilink> context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.<br />For example, <wikilink article='animal rights'>animal rights</wikilink> activists and <wikilink article='Conservation movement'>conservation</wikilink> authorities may request fishing or hunting moratoria to protect <wikilink article='endangered'>endangered</wikilink> or threatened animal species.These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.<br />Another instance is a delay of legal obligations or payment (<italic><wikilink article='debt moratorium'>debt moratorium</wikilink></italic>). A legal official can order <template name='clarify'><parameter key='text' value='a delay of payment' /><parameter key='date' value='December 2015' /></template> due to extenuating circumstances, which render one party incapable of paying another.<ref><template name='cite web'><parameter key='url' value='http://dictionary.reference.com/browse/moratorium?s=t' /><parameter key='title' value='definition of moratorium' /><parameter key='author' value='dictionary.com' /><parameter key='work' value='dictionary.com' /></template></ref><br /><section level='2' title='See also'>
            <template name='wiktionary'><parameter value='moratorium' /></template>
            <list type='unordered'><listItem level='1'><wikilink article='Justice delayed is justice denied'>Justice delayed is justice denied</wikilink></listItem><listItem level='1'><wikilink article='2010 U.S. Deepwater Drilling Moratorium'>2010 U.S. Deepwater Drilling Moratorium</wikilink></listItem><listItem level='1'><wikilink article='Moratorium to End the War in Vietnam'>Moratorium to End the War in Vietnam</wikilink></listItem><listItem level='1'><wikilink article='UN moratorium on the death penalty'>UN moratorium on the death penalty</wikilink></listItem></list>
            </section><section level='2' title='References'>
            <template name='Reflist' />
            <list type='unordered'><listItem level='1'><template name='cite NIE'><parameter key='wstitle' value='Moratorium' /><parameter key='year' value='1905' /></template></listItem></list>
            <template name='Authority control' /><br /><br />
            <template name='Law-term-stub' /><br /></section></article>""";

    WikiTextNode root =
        (WikiTextNode)
            ParseTreeBuilder.visitTreeFromText(article, List.of(new TestErrorListener()), true);
    Assertions.assertEquals(
        xml, com.lucaskjaerozhang.wikitext_parser.WikiTextParser.writeToString(root));

    Assertions.assertIterableEquals(
        List.of(
            "Authority control",
            "Law-term-stub",
            "Reflist",
            "Short description",
            "cite NIE",
            "cite web",
            "clarify",
            "more citations needed",
            "wiktionary"),
        root.getTemplates().stream().sorted().toList());
  }
}
