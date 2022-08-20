package com.lucaskjaerozhang.wikitext_parser.grammar;

import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests markup elements from https://en.wikipedia.org/wiki/Help:Wikitext#Layout
 *
 * <p>Tests both the lexer and parser at the same time because we only care that the grammar is
 * correct.
 */
class TemplateGrammarTest extends WikitextBaseTest {
  @Test
  void templateWithNoParameters() {
    final String templateWithNoParameters = "{{Transclusion demo}}";
    final String templateWithNoParametersXML =
        "<article><template name='Transclusion demo' /></article>";

    testTranslation(templateWithNoParameters, templateWithNoParametersXML);
  }

  @Test
  void asOfTemplate() {
    final String asOf = "{{As of|2009|4|df=us}}";
    final String asOfXML =
        "<article><template name='As of'><parameter value='2009' /><parameter value='4' /><parameter key='df' value='us' /></template></article>";
    testTranslation(asOf, asOfXML);
  }

  @Test
  void templateImplementation() {
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
    final String hoverTitleXML = "";
    testTranslation(hoverTitle, hoverTitleXML);
  }
}
