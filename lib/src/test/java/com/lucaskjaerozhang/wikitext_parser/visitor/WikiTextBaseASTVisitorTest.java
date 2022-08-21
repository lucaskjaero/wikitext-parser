package com.lucaskjaerozhang.wikitext_parser.visitor;

import com.lucaskjaerozhang.wikitext_parser.TestErrorListener;
import com.lucaskjaerozhang.wikitext_parser.ast.root.Article;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Not going overboard in testing the base visitor since it pretty much does nothing. The visitor
 * interface itself will be used in XML parsing, so we can test it robustly that way.
 */
class WikiTextBaseASTVisitorTest {
  private static class TestVisitor extends WikiTextBaseASTVisitor<String> {}

  @Test
  void baseVisitorsReturnEmptyOptional() {
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
    Article root =
        (Article)
            ParseTreeBuilder.visitTreeFromText(article, List.of(new TestErrorListener()), false);
    TestVisitor visitor = new TestVisitor();
    Optional<String> result = visitor.visitArticle(root);

    Assertions.assertTrue(result.isEmpty());
  }
}