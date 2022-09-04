package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

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
  void templateProcessorDoesNotPassInIncludOnlyTags() {
    final String test =
        """
                {{Main other|{{Top icon
                | imagename    = symbol support vote.svg
                | wikilink     = Wikipedia:Good articles
                | description  = This is a good article. Click here for more information.
                | id           = good-star
                | maincat      = [[Category:Good articles]]
                }}|<includeonly>{{Error|[[Template:Good article]] is only for [[Wikipedia:Good articles]].}}</includeonly>
                }}<noinclude>
                {{documentation}}
                </noinclude>
                """;
    final String expected =
        """
            {{Main other|{{Top icon
            | imagename    = symbol support vote.svg
            | wikilink     = Wikipedia:Good articles
            | description  = This is a good article. Click here for more information.
            | id           = good-star
            | maincat      = [[Category:Good articles]]
            }}|{{Error|[[Template:Good article]] is only for [[Wikipedia:Good articles]].}}
            }}
            """;

    class GoodArticleTemplateProvider implements TemplateProvider {
      @Override
      public Optional<String> getTemplate(String template) {
        switch (template) {
          case "Template:test":
            return Optional.of(test);
          default:
            Assertions.fail(String.format("Not expecting template %s to be needed", template));
            return Optional.empty();
        }
      }
    }

    TemplateProcessor processor = new TemplateProcessor();
    String result =
        processor.processTemplate("test", new GoodArticleTemplateProvider(), Set.of(), List.of());
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
