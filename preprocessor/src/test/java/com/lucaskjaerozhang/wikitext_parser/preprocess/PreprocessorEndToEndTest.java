package com.lucaskjaerozhang.wikitext_parser.preprocess;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PreprocessorEndToEndTest {
  public static void testPreprocessor(String input, String expected) {
    Preprocessor preprocessor = new Preprocessor(new PreprocessorVariables(Map.of()));
    String result = preprocessor.preprocess(input);
    Assertions.assertEquals(expected, result);
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
}
