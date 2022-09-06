package com.lucaskjaerozhang.wikitext_parser.visitor;

import com.lucaskjaerozhang.wikitext_parser.TestErrorListener;
import com.lucaskjaerozhang.wikitext_parser.ast.root.Article;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
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

    class WikipediaTestTemplateProvider implements TemplateProvider {
      public Optional<String> getTemplate(String template) {
        String result =
            switch (template) {
              case "Template:Short description" -> "<template name='Short description'><parameter value='Delay or suspension of an activity or a law' /></template>";
              case "Template:more citations needed" -> "<template name='more citations needed'><parameter key='date' value='April 2009' /></template>";
              case "Template:clarify" -> "<template name='clarify'><parameter key='text' value='a delay of payment' /><parameter key='date' value='December 2015' /></template>";
              case "Template:cite nie" -> "<template name='cite NIE'><parameter key='wstitle' value='Moratorium' /><parameter key='year' value='1905' /></template>";
              case "Template:cite web" -> "<template name='cite web'><parameter key='url' value='http://dictionary.reference.com/browse/moratorium?s=t' /><parameter key='title' value='definition of moratorium' /><parameter key='author' value='dictionary.com' /><parameter key='work' value='dictionary.com' /></template>";
              case "Template:Authority control" -> "<template name='Authority control' />";
              case "Template:Reflist" -> "<template name='Reflist' />";
              case "Template:Law-term-stub" -> "<template name='Law-term-stub' />";
              case "Template:wiktionary" -> "<template name='wiktionary'><parameter value='moratorium' /></template>";
              default -> String.format(
                  "%s",
                  Assertions.fail(
                      String.format("Not expecting template '%s' to be needed", template)));
            };
        return Optional.of(result);
      }
    }

    Article root =
        (Article)
            ParseTreeBuilder.visitTreeFromText(
                article,
                new WikipediaTestTemplateProvider(),
                List.of(new TestErrorListener()),
                false);
    TestVisitor visitor = new TestVisitor();
    Optional<String> result = visitor.visitArticle(root);

    Assertions.assertTrue(result.isEmpty());
  }
}
