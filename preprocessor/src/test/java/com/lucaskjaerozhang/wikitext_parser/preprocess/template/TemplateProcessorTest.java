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
}
