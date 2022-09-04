package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import com.lucaskjaerozhang.wikitext_parser.preprocess.TestTemplateProvider;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TemplateProcessorTest {
  @Test
  void templateProcessorCanHandlePositionalParameters() {
    final String hoverTitle =
        """
                    <noinclude>{{Being deleted|2020 December 19|Template:Hover_title_and_Template:Tooltip|merge=Template:Tooltip}}</noinclude>{{#ifeq:{{yesno-no|{{{link}}}}}|yes
                     |[[{{{2}}}|<span title="{{{1}}}" class="rt-commentedText" {{#ifeq:{{yesno-yes|{{{dotted}}}}}|no|
                      |style="border-bottom:1px dotted"
                     }}>{{{2}}}</span>]]
                     |<span title="{{{1}}}" class="rt-commentedText" {{#ifeq:{{yesno-yes|{{{dotted}}}}}|no|
                      |style="border-bottom:1px dotted"
                     }}>{{{2}}}</span>
                    }}<noinclude>
                    {{documentation}}
                    </noinclude>
                    """;
    final String expected =
        """
                  {{#ifeq:nolink|yes
                   |[[second|<span title="title" class="rt-commentedText" {{#ifeq:yestrue|no|
                    |style="border-bottom:1px dotted"
                   }}>second</span>]]
                   |<span title="title" class="rt-commentedText" {{#ifeq:yestrue|no|
                    |style="border-bottom:1px dotted"
                   }}>second</span>
                  }}
                  """;

    class HoverTitleTestTemplateProvider implements TemplateProvider {
      @Override
      public Optional<String> getTemplate(String template) {
        switch (template) {
          case "Template:hovertitle":
            return Optional.of(hoverTitle);
          case "Template:yesno-no":
            return Optional.of("no{{{1}}}");
          case "Template:yesno-yes":
            return Optional.of("yes{{{1}}}");
          default:
            Assertions.fail(String.format("Not expecting template %s to be needed", template));
            return Optional.empty();
        }
      }
    }

    TemplateProcessor processor = new TemplateProcessor();
    String result =
        processor.processTemplate(
            "hovertitle",
            new HoverTitleTestTemplateProvider(),
            Set.of(),
            List.of("title", "second", "dotted=true", "link=link"));
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
                    -->All articles containing potentially dated statements}}</includeonly><!--{{As of}} end--><noinclude><!-- \n
                    ## dynamic example (as of today, self ref)

                    -->{{As of|{{CURRENTYEAR}}|{{CURRENTMONTH}}|{{CURRENTDAY}}}}<sup class="plainlinks">[https://en.wikipedia.org/wiki/Template:As_of &#91;ref&#93;]</sup>

                    {{documentation}}

                    <!-- Add categories and inter-wikis to the /doc subpage, not here! --> \n
                    </noinclude>{{#invoke:Check for unknown parameters|check|unknown={{main other|[[Category:Pages using as of template with unknown parameters|_VALUE_{{PAGENAME}}]]}}|preview=Page using [[Template:As of]] with unknown parameter "_VALUE_"|ignoreblank=y| 1 | 2 | 3 | alt | bare | df | lc | post | pre | since | url }}""";
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
                    -->{{#if: altText | altText | {{#if:||{{#if:|{{#ifeq: {{{lc}}} |  | s | S }}ince | {{#ifeq: {{{lc}}} |  | a | A }}s of}}}} {{#if:|{{{pre}}}&#32;}}{{#if:  | {{#ifeq:|us|Sep&nbsp;{{#expr:{{{3}}}}},&#32;|{{#expr:{{{3}}}}}&nbsp;Sep&nbsp;}}1992|{{#if: Sep |Sep&nbsp;}}1992}}}}{{#if:|<nowiki />{{{post}}}}}<!--
                    #### That nowiki is required or, various characters (:, ;, #, and *} will be parsed as wikisyntax for list items.
                    ## ref/update inline banner (hidden by default)
                    --><sup class="plainlinks noexcerpt noprint asof-tag update" style="display:none;">[{{#if:|{{{url}}} &#91;ref&#93;|{{fullurl:as of|action=edit}} &#91;update&#93;}}]</sup><!--

                    ## categorisation disabled outside main namespace
                    -->{{DMCA|Articles containing potentially dated statements|from|<!--

                    ## temporal category for template (word method)
                    -->{{#if: {{#ifexpr: 1992 = 1 }} | {{#ifexpr: {{#time:Y|1992}} > 2004 | 1992 | {{#ifexpr: {{#time:Y|1992}} > 1989 | {{#time:Y|1992}} | before 1990 }}}} | <!--

                    ## temporal category for template (parameter method)
                    -->{{#ifexpr: 1992 > 2004 | {{#if:Sep | Sep }} 1992 | {{#ifexpr:1992 > 1989 | 1992 | before 1990 }}}}}}|<!--

                    ## global category for template
                    -->All articles containing potentially dated statements}}""";

    class AsOfTestTemplateProvider extends TestTemplateProvider {
      @Override
      public Optional<String> getTemplate(String template) {
        return template.equals("Template:as of") ? Optional.of(asOf) : super.getTemplate(template);
      }
    }

    TemplateProcessor processor = new TemplateProcessor();
    String result =
        processor.processTemplate(
            "as of",
            new AsOfTestTemplateProvider(),
            Set.of(),
            List.of("1992", "Sep", "alt=altText"));
    Assertions.assertEquals(expected, result);
  }

  @Test
  void templateProcessorCanHandleMultilineTemplates() {
    final String test =
        """
            {{asbox
            | image     = Scale of justice 2.svg
            | pix       = 22
            | qualifier =\s
            | category  = Legal terminology stubs
            | tempsort  =\s
            | name      = Template:Law-term-stub
            }}
            """;
    final String expected = "<module name='Asbox'><argument>main</argument></module>\n";

    class AsBoxTestTemplateProvider implements TemplateProvider {
      @Override
      public Optional<String> getTemplate(String template) {
        switch (template) {
          case "Template:test":
            return Optional.of(test);
          case "Template:asbox":
            return Optional.of(
                "<includeonly>{{#invoke:Asbox|main}}</includeonly><noinclude>\\n{{documentation}}\\n<!-- Add categories to the /doc subpage and interwikis to Wikidata. -->\\n</noinclude>");
          default:
            Assertions.fail(String.format("Not expecting template %s to be needed", template));
            return Optional.empty();
        }
      }
    }

    TemplateProcessor processor = new TemplateProcessor();
    String result =
        processor.processTemplate("test", new AsBoxTestTemplateProvider(), Set.of(), List.of());
    Assertions.assertEquals(expected, result);
  }
}
