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
             }}>second</span>]]
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
}
