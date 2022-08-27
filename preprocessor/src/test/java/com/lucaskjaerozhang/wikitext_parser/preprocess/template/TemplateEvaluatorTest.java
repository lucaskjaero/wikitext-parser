package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TemplateEvaluatorTest {
  @Test
  void templateEvaluatorCanSubstituteVariables() {
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

    TemplateEvaluator evaluator = new TemplateEvaluator();
    String result =
        evaluator.evaluateTemplate(
            hoverTitle, List.of("title", "second"), Map.of("dotted", "true", "link", "link"));
    Assertions.assertEquals(expected, result);
  }

  @Test
  void templateEvaluatorHandlesOnlyIncludeBlocks() {
    final String asOf =
        """
                  <!--{{As of}} begin--><includeonly><!--

                  ## param 1 is the year
                  ## optional param 2 is the month
                  ## optional param 3 is the day of the month
                  ## optional named parameter alt=[text] is alternative display text (may include wiki markup)
                  ## optional named parameter df=US produces American date format in displayed text
                  ## optional named parameter lc=on gives lower-case-first output
                  ## optional named parameter url=[URL] gives statement reference

                  ## displayed text ([A/a]s of [Start date] by default)
                  -->{{#if: {{{alt|}}} | {{{alt}}} | {{#if:{{{bare|}}}||{{#if:{{{since|}}}|{{#ifeq: {{{lc}}} | {{{lc|}}} | s | S }}ince | {{#ifeq: {{{lc}}} | {{{lc|}}} | a | A }}s of}}}} {{#if:{{{pre|}}}|{{{pre}}}&#32;}}{{#if: {{{3|}}} | {{#ifeq:{{lc:{{{df|}}}}}|us|{{MONTHNAME|{{{2}}}}}&nbsp;{{#expr:{{{3}}}}},&#32;|{{#expr:{{{3}}}}}&nbsp;{{MONTHNAME|{{{2}}}}}&nbsp;}}{{{1}}}|{{#if: {{{2|}}} |{{MONTHNAME|{{{2}}}}}&nbsp;}}{{{1}}}}}}}{{#if:{{{post|}}}|<nowiki />{{{post}}}}}<!--
                  #### That nowiki is required or, various characters (:, ;, #, and *} will be parsed as wikisyntax for list items.
                  ## ref/update inline banner (hidden by default)
                  --><sup class="plainlinks noexcerpt noprint asof-tag {{#if:{{{url|}}}|ref|update}}" style="display:none;">[{{#if:{{{url|}}}|{{{url}}} &#91;ref&#93;|{{fullurl:{{PAGENAME}}|action=edit}} &#91;update&#93;}}]</sup><!--

                  ## categorisation disabled outside main namespace
                  -->{{DMCA|Articles containing potentially dated statements|from|<!--

                  ## temporal category for template (word method)
                  -->{{#if: {{#ifexpr: {{{1}}} = 1 }} | {{#ifexpr: {{#time:Y|{{{1}}}}} > 2004 | {{{1}}} | {{#ifexpr: {{#time:Y|{{{1}}}}} > 1989 | {{#time:Y|{{{1}}}}} | before 1990 }}}} | <!--

                  ## temporal category for template (parameter method)
                  -->{{#ifexpr: {{{1}}} > 2004 | {{#if:{{{2|}}} | {{MONTHNAME|{{{2}}}}} }} {{{1}}} | {{#ifexpr:{{{1}}} > 1989 | {{{1}}} | before 1990 }}}}}}|<!--

                  ## global category for template
                  -->All articles containing potentially dated statements}}</includeonly><!--{{As of}} end--><noinclude><!--\s

                  ## dynamic example (as of today, self ref)

                  -->{{As of|{{CURRENTYEAR}}|{{CURRENTMONTH}}|{{CURRENTDAY}}}}<sup class="plainlinks">[https://en.wikipedia.org/wiki/Template:As_of &#91;ref&#93;]</sup>

                  {{documentation}}

                  <!-- Add categories and inter-wikis to the /doc subpage, not here! -->\s

                  </noinclude>{{#invoke:Check for unknown parameters|check|unknown={{main other|[[Category:Pages using as of template with unknown parameters|_VALUE_{{PAGENAME}}]]}}|preview=Page using [[Template:As of]] with unknown parameter "_VALUE_"|ignoreblank=y| 1 | 2 | 3 | alt | bare | df | lc | post | pre | since | url }}
                  """;
    final String expected =
        """
                <!--

                ## param 1 is the year
                ## optional param 2 is the month
                ## optional param 3 is the day of the month
                ## optional named parameter alt=[text] is alternative display text (may include wiki markup)
                ## optional named parameter df=US produces American date format in displayed text
                ## optional named parameter lc=on gives lower-case-first output
                ## optional named parameter url=[URL] gives statement reference

                ## displayed text ([A/a]s of [Start date] by default)
                -->{{#if: {{{alt|}}} | {{{alt}}} | {{#if:{{{bare|}}}||{{#if:{{{since|}}}|{{#ifeq: {{{lc}}} | {{{lc|}}} | s | S }}ince | {{#ifeq: {{{lc}}} | {{{lc|}}} | a | A }}s of}}}} {{#if:{{{pre|}}}|{{{pre}}}&#32;}}{{#if: {{{3|}}} | {{#ifeq:{{lc:{{{df|}}}}}|us|{{MONTHNAME|Sep}}&nbsp;{{#expr:{{{3}}}}},&#32;|{{#expr:{{{3}}}}}&nbsp;{{MONTHNAME|Sep}}&nbsp;}}1992|{{#if: Sep |{{MONTHNAME|Sep}}&nbsp;}}1992}}}}{{#if:{{{post|}}}|<nowiki />{{{post}}}}}<!--
                #### That nowiki is required or, various characters (:, ;, #, and *} will be parsed as wikisyntax for list items.
                ## ref/update inline banner (hidden by default)
                --><sup class="plainlinks noexcerpt noprint asof-tag {{#if:{{{url|}}}|ref|update}}" style="display:none;">[{{#if:{{{url|}}}|{{{url}}} &#91;ref&#93;|{{fullurl:{{PAGENAME}}|action=edit}} &#91;update&#93;}}]</sup><!--

                ## categorisation disabled outside main namespace
                -->{{DMCA|Articles containing potentially dated statements|from|<!--

                ## temporal category for template (word method)
                -->{{#if: {{#ifexpr: 1992 = 1 }} | {{#ifexpr: {{#time:Y|1992}} > 2004 | 1992 | {{#ifexpr: {{#time:Y|1992}} > 1989 | {{#time:Y|1992}} | before 1990 }}}} | <!--

                ## temporal category for template (parameter method)
                -->{{#ifexpr: 1992 > 2004 | {{#if:Sep | {{MONTHNAME|Sep}} }} 1992 | {{#ifexpr:1992 > 1989 | 1992 | before 1990 }}}}}}|<!--

                ## global category for template
                -->All articles containing potentially dated statements}}
                """;

    TemplateEvaluator evaluator = new TemplateEvaluator();
    String result =
        evaluator.evaluateTemplate(asOf, List.of("1992", "Sep"), Map.of("alt", "altText"));
    Assertions.assertEquals(expected, result);
  }
}
