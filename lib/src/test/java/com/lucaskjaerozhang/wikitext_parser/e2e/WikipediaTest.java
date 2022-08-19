package com.lucaskjaerozhang.wikitext_parser.e2e;

import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
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
    String xml = "";
    testTranslation(article, xml);
  }
}
