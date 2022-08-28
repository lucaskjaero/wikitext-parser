package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TemplateProcessorTest {
  @Test
  void templateInvocationParserCanHandlePositionalParameters() {
    final String hoverTitle =
        """
                    <noinclude>{{Being deleted|2020 December 19|Template:Hover_title_and_Template:Tooltip|merge=Template:Tooltip}}</noinclude>{{#ifeq:{{yesno-no|{{{link}}}}}|yes
                     |[[{{{2}}}|<span title="{{{1}}}" class="rt-commentedText" {{#ifeq:{{yesno-yes|{{{dotted}}}}}|no|
                      |style="border-bottom:1px dotted"
                     }}>{{{2}}}</span>]]{{{3}}}
                     |<span title="{{{1}}}" class="rt-commentedText" {{#ifeq:{{yesno-yes|{{{dotted}}}}}|no|
                      |style="border-bottom:1px dotted"
                     }}>{{{2}}}</span>
                    }}<noinclude>
                    {{documentation}}
                    </noinclude>
                    """;
    final String expected =
        """
                  {{#ifeq:{{yesno-no|link}}|yes
                   |[[second|<span title="title" class="rt-commentedText" {{#ifeq:{{yesno-yes|true}}|no|
                    |style="border-bottom:1px dotted"
                   }}>second</span>]]{{{3}}}
                   |<span title="title" class="rt-commentedText" {{#ifeq:{{yesno-yes|true}}|no|
                    |style="border-bottom:1px dotted"
                   }}>second</span>
                  }}
                  """;

    class TestTemplateProvider implements TemplateProvider {
      @Override
      public String getTemplate(String template) {
        switch (template) {
          case "hovertitle":
            return hoverTitle;
          case "yesno-no":
            return "no{{{1}}}";
          case "yesno-yes":
            return "yes{{{1}}}";
          default:
            Assertions.fail(String.format("Not expecting template %s to be needed", template));
            return "";
        }
      }
    }

    TemplateProcessor processor = new TemplateProcessor();
    String result =
        processor.processTemplate(
            "hovertitle",
            new TestTemplateProvider(),
            List.of("title", "second", "dotted=true", "link=link"));
    Assertions.assertEquals(expected, result);
  }

  //  @Test
  //  void templateEvaluatorHandlesOnlyIncludeBlocks() {
  //    final String asOf =
  //        """
  //                <!--
  //
  //                ## param 1 is the year
  //                ## optional param 2 is the month
  //                ## optional param 3 is the day of the month
  //                ## optional named parameter alt=[text] is alternative display text (may include
  // wiki markup)
  //                ## optional named parameter df=US produces American date format in displayed
  // text
  //                ## optional named parameter lc=on gives lower-case-first output
  //                ## optional named parameter url=[URL] gives statement reference
  //
  //                ## displayed text ([A/a]s of [Start date] by default)
  //                -->{{#if: altText | altText | {{#if:||{{#if:|{{#ifeq: {{{lc}}} |  | s | S }}ince
  // | {{#ifeq: {{{lc}}} |  | a | A }}s of}}}} {{#if:|{{{pre}}}&#32;}}{{#if:  |
  // {{#ifeq:{{lc:}}|us|{{MONTHNAME|Sep}}&nbsp;{{#expr:{{{3}}}}},&#32;|{{#expr:{{{3}}}}}&nbsp;{{MONTHNAME|Sep}}&nbsp;}}1992|{{#if: Sep |{{MONTHNAME|Sep}}&nbsp;}}1992}}}}{{#if:|<nowiki />{{{post}}}}}<!--
  //                #### That nowiki is required or, various characters (:, ;, #, and *} will be
  // parsed as wikisyntax for list items.
  //                ## ref/update inline banner (hidden by default)
  //                --><sup class="plainlinks noexcerpt noprint asof-tag {{#if:|ref|update}}"
  // style="display:none;">[{{#if:|{{{url}}} &#91;ref&#93;|{{fullurl:{{PAGENAME}}|action=edit}}
  // &#91;update&#93;}}]</sup><!--
  //
  //                ## categorisation disabled outside main namespace
  //                -->{{DMCA|Articles containing potentially dated statements|from|<!--
  //
  //                ## temporal category for template (word method)
  //                -->{{#if: {{#ifexpr: 1992 = 1 }} | {{#ifexpr: {{#time:Y|1992}} > 2004 | 1992 |
  // {{#ifexpr: {{#time:Y|1992}} > 1989 | {{#time:Y|1992}} | before 1990 }}}} | <!--
  //
  //                ## temporal category for template (parameter method)
  //                -->{{#ifexpr: 1992 > 2004 | {{#if:Sep | {{MONTHNAME|Sep}} }} 1992 |
  // {{#ifexpr:1992 > 1989 | 1992 | before 1990 }}}}}}|<!--
  //
  //                ## global category for template
  //                -->All articles containing potentially dated statements}}""";
  //
  //    TemplateInvocationParser finder = new TemplateInvocationParser();
  //    List<TemplateInvocation> result = finder.findInvocations(asOf).stream().sorted().toList();
  //    Assertions.assertIterableEquals(List.of(
  //            new TemplateInvocation("yesno-no", List.of("link"), Map.of()),
  //            new TemplateInvocation("yesno-yes", List.of("true"), Map.of()),
  //            new TemplateInvocation("yesno-yes", List.of("true"), Map.of())
  //    ), result);
  //  }
}
