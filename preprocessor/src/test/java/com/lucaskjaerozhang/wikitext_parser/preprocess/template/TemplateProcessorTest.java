package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.DummyTemplateProvider;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
                  [[second|<span title="title" class="rt-commentedText" >second</span>]]
                  """;

    class HoverTitleTestTemplateProvider implements TemplateProvider {
      @Override
      public Optional<String> getTemplate(String template) {
        switch (template) {
          case "Template:hovertitle":
            return Optional.of(hoverTitle);
          case "Template:yesno-no":
            return Optional.of("{{{1}}}");
          case "Template:yesno-yes":
            return Optional.of("{{{1}}}");
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
            List.of(),
            List.of("title", "second"),
            Map.of("dotted", "no", "link", "yes"));
    Assertions.assertEquals(expected, result);
  }

  @Test
  void templateProcessorDoesNotPassInIncludeOnlyTags() {
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
            <template name='top icon'><argument name='imagename'>symbol support vote.svg</argument><argument name='wikilink'>Wikipedia:Good articles</argument><argument name='description'>This is a good article. Click here for more information.</argument><argument name='id'>good-star</argument><argument name='maincat'>[[Category:Good articles]]</argument></template> or [[Template:Good article]] is only for [[Wikipedia:Good articles]].
            """;

    class GoodArticleTemplateProvider implements TemplateProvider {
      @Override
      public Optional<String> getTemplate(String template) {
        switch (template) {
          case "Template:Error":
            return Optional.of("{{{1}}}");
          case "Template:Main other":
            return Optional.of("{{{1}}} or {{{2}}}");
          case "Template:test":
            return Optional.of(test);
          case "Template:Top icon":
            return Optional.of(
                "<template name='top icon'><argument name='imagename'>{{{imagename}}}</argument><argument name='wikilink'>{{{wikilink}}}</argument><argument name='description'>{{{description}}}</argument><argument name='id'>{{{id}}}</argument><argument name='maincat'>{{{maincat}}}</argument></template>");
          default:
            Assertions.fail(String.format("Not expecting template %s to be needed", template));
            return Optional.empty();
        }
      }
    }

    TemplateProcessor processor = new TemplateProcessor();
    String result =
        processor.processTemplate(
            "test", new GoodArticleTemplateProvider(), List.of(), List.of(), Map.of());
    Assertions.assertEquals(expected, result);
  }

  @Test
  void templateProcessorCanHandleMultilineTemplates() {
    final String test =
        """
            {{asbox
            | image     = Scale of justice 2.svg
            | pix       = 22
            | subject   = [[law|legal term]]\s
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
        processor.processTemplate(
            "test", new AsBoxTestTemplateProvider(), List.of(), List.of(), Map.of());
    Assertions.assertEquals(expected, result);
  }

  @Test
  void templateProcessorPreservesParentFrameArgumentsForModules() {
    class ModuleTemplateProvider implements TemplateProvider {
      @Override
      public Optional<String> getTemplate(String template) {
        return switch (template) {
          case "Template:coord" ->
              Optional.of("<includeonly>{{#invoke:Coordinates|coord}}</includeonly>");
          default ->
              Optional.of(
                  String.format(
                      "%s",
                      Assertions.fail(
                          String.format("Not expecting template %s to be needed", template))));
        };
      }
    }

    TemplateProcessor processor = new TemplateProcessor();
    String result =
        processor.processTemplate(
            "coord",
            new ModuleTemplateProvider(),
            List.of(),
            List.of("36", "0", "3", "N", "120", "7", "30", "E"),
            Map.of("display", "title"));

    Assertions.assertEquals(
        "<module name='Coordinates'><argument>coord</argument><argument>36</argument><argument>0</argument><argument>3</argument><argument>N</argument><argument>120</argument><argument>7</argument><argument>30</argument><argument>E</argument><argument>display=title</argument></module>",
        result);
  }

  @Test
  void templateProcessorCanHandleTemplatesThatCallThemselves() {
    TemplateProcessor processor = new TemplateProcessor();

    // Contrived examples that demonstrate the rules
    Assertions.assertDoesNotThrow(testRecursionDetection(processor, List.of()));
    Assertions.assertDoesNotThrow(testRecursionDetection(processor, List.of("test")));
    Assertions.assertDoesNotThrow(testRecursionDetection(processor, List.of("test", "other")));
    Assertions.assertThrows(
        IllegalArgumentException.class, testRecursionDetection(processor, List.of("test", "test")));
    Assertions.assertThrows(
        IllegalArgumentException.class,
        testRecursionDetection(
            processor, IntStream.range(0, 101).mapToObj(String::valueOf).toList()));

    // A realistic test to make sure the stack actually is being created properly.
    class RecursiveTemplateProvider implements TemplateProvider {
      private int calls = 0;

      @Override
      public Optional<String> getTemplate(String template) {
        calls++;
        if (calls > 3) {
          Assertions.fail("Recursion was not correctly detected after 3 calls");
        }

        return Optional.of("{{test}}");
      }
    }
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            processor.processTemplate(
                "test", new RecursiveTemplateProvider(), List.of(), List.of(), Map.of()));
  }

  private Executable testRecursionDetection(TemplateProcessor processor, List<String> stack) {
    return () ->
        processor.processTemplate("test", new DummyTemplateProvider(), stack, List.of(), Map.of());
  }
}
