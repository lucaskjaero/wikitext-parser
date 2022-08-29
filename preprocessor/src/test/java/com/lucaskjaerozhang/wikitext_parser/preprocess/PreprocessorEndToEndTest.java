package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PreprocessorEndToEndTest {
  public static void testPreprocessor(String input, String expected) {
    Preprocessor preprocessor =
        new Preprocessor(new PreprocessorVariables(Map.of()), new TestTemplateProvider());
    String result = preprocessor.preprocess(input, true);
    Assertions.assertEquals(expected, result);
  }

  static class TestTemplateProvider implements TemplateProvider {
    private String makeTemplatePlaceholder(String templateName, List<String> parameterNames) {
      String parameters =
          parameterNames.stream()
              .map(p -> String.format("<parameter name='%s'>{{{%s}}}</parameter>", p, p))
              .collect(Collectors.joining(""));
      return String.format("<template name='%s'>%s</template>", templateName, parameters);
    }

    @Override
    public String getTemplate(String template) {
      return switch (template) {
        case "Authority control" -> makeTemplatePlaceholder("Authority control", List.of());
        case "Law-term-stub" -> String.format(
            "%s{{clarify|text=recursion|date=today}}",
            makeTemplatePlaceholder("Law-term-stub", List.of()));
        case "Short description" -> makeTemplatePlaceholder("Short description", List.of("1"));
        case "Reflist" -> makeTemplatePlaceholder("Reflist", List.of());
        case "cite NIE" -> makeTemplatePlaceholder("cite NIE", List.of("wstitle", "year"));
        case "cite web" -> makeTemplatePlaceholder(
            "cite web", List.of("url", "title", "author", "work"));
        case "clarify" -> makeTemplatePlaceholder("clarify", List.of("text", "date"));
        case "more citations needed" -> makeTemplatePlaceholder(
            "more citations needed", List.of("date"));
        case "wiktionary" -> makeTemplatePlaceholder("wiktionary", List.of("1"));
        default -> String.format(
            "%s",
            Assertions.fail(String.format("Not expecting template '%s' to be needed", template)));
      };
    }
  }

  @Test
  void asOf() {
    final String asOfTemplate =
        """
        <noinclude>{{Being deleted|2020 December 19|Template:Hover_title_and_Template:Tooltip|merge=Template:Tooltip}}</noinclude>{{#ifeq:{{yesno-no|link}}|yes
         |[[second|<span title="title" class="rt-commentedText" {{#ifeq:{{yesno-yes|true}}|no|
          |style="border-bottom:1px dotted"
         }}>second</span>]]
         |<span title="title" class="rt-commentedText" {{#ifeq:{{yesno-yes|true}}|no|
          |style="border-bottom:1px dotted"
         }}>second</span>
        }}<noinclude>
        {{documentation}}
        </noinclude>
        """;
    final String asOfXML =
        """
            <noinclude>{{Being deleted|2020 December 19|Template:Hover_title_and_Template:Tooltip|merge=Template:Tooltip}}</noinclude>{{#ifeq:{{yesno-no|link}}|yes
             |[[second|<span title="title" class="rt-commentedText" {{#ifeq:{{yesno-yes|true}}|no|
              |style="border-bottom:1px dotted"
             }}>second</span>]]
             |<span title="title" class="rt-commentedText" {{#ifeq:{{yesno-yes|true}}|no|
              |style="border-bottom:1px dotted"
             }}>second</span>
            }}<noinclude>
            {{documentation}}
            </noinclude>
            """;
    testPreprocessor(asOfTemplate, asOfXML);
  }

  @Test
  void moratoriumTest() {
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


                    {{Law-term-stub}}""";

    String processed =
        """
            <template name='Short description'><parameter name='1'>Delay or suspension of an activity or a law</parameter></template>
            <template name='more citations needed'><parameter name='date'>April 2009</parameter></template>
            A '''moratorium''' is a delay or suspension of an activity or a law. In a [[legal]] context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.

            For example, [[animal rights]] activists and [[Conservation movement|conservation]] authorities may request fishing or hunting moratoria to protect [[endangered]] or threatened animal species.  These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.

            Another instance is a delay of legal obligations or payment (''[[debt moratorium]]''). A legal official can order <template name='clarify'><parameter name='text'>a delay of payment</parameter><parameter name='date'>December 2015</parameter></template> due to extenuating circumstances, which render one party incapable of paying another.<ref><template name='cite web'><parameter name='url'>http://dictionary.reference.com/browse/moratorium?s=t</parameter><parameter name='title'>definition of moratorium</parameter><parameter name='author'>dictionary.com</parameter><parameter name='work'>dictionary.com</parameter></template></ref>

            ==See also==
            <template name='wiktionary'><parameter name='1'>moratorium</parameter></template>
            *[[Justice delayed is justice denied]]
            *[[2010 U.S. Deepwater Drilling Moratorium]]
            *[[Moratorium to End the War in Vietnam]]
            *[[UN moratorium on the death penalty]]

            ==References==
            <template name='Reflist'></template>
            *<template name='cite NIE'><parameter name='wstitle'>Moratorium</parameter><parameter name='year'>1905</parameter></template>

            <template name='Authority control'></template>

            [[Category:Legal terminology]]


            <template name='Law-term-stub'></template><template name='clarify'><parameter name='text'>recursion</parameter><parameter name='date'>today</parameter></template>""";

    testPreprocessor(article, processed);
  }
}
