package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class MoratoriumTest extends PreprocessorEndToEndTest {
  public MoratoriumTest() {
    super("wikipedia", "en", "Moratorium_(law)");
  }

  @Test
  void moratoriumTest() {
    //    endToEndTest();
  }

  /** Short description breakdown */
  @Test
  void shortDescriptionTest() {
    testPreprocessorWithFile(
        "{{Short description|Delay or suspension of an activity or a law}}", "short_description");
  }

  @Test
  void testLongShortDescription() {
    testPreprocessorWithString(
        "{{#ifexpr: {{#invoke:String|len|Delay or suspension of an activity or a law}}>100 | [[Category:{{safesubst:#invoke:pagetype|main}} with long short description]]}}",
        "<ifexpr><conditional><module name='String'><argument>len</argument><argument>Delay or suspension of an activity or a law</argument></module>>100</conditional><ifTrue>[[Category:<module name='pagetype'><argument>main</argument></module> with long short description]]</ifTrue><ifFalse></ifFalse></ifexpr>");
  }

  /**
   * Breaking down the unknown parameters check from {{Short Description|Delay or suspension of an
   * activity or a law}}
   */
  @Test
  void checkUnknownParametersTest() {
    testPreprocessorWithString(
        "{{#invoke:Check for unknown parameters|check|unknown={{Main other|[[Category:Pages using short description with unknown parameters|_VALUE_{{PAGENAME}}]]}}|preview=Page using [[Template:Short description]] with unknown parameter \"_VALUE_\"|ignoreblank=y| 1 | 2 | pagetype | bot |plural }}",
        "<module name='Check for unknown parameters'><argument>check</argument><argument>unknown=</argument><argument>preview=Page using [[Template:Short description]] with unknown parameter \"_VALUE_\"</argument><argument>ignoreblank=y</argument><argument>1</argument><argument>2</argument><argument>pagetype</argument><argument>bot</argument><argument>plural</argument></module>");
  }

  /**
   * Breaking down {{Short description/lowercasecheck}} into parts {{Short
   * description/lowercasecheck|Delay or suspension of an activity or a law}}
   */
  @Test
  void shortDescriptionLowercase() {
    testPreprocessorWithString(
        "{{main other|[[Category:Pages with lower-case short description|{{trim|Delay or suspension of an activity or a law}}]]}}",
        "");
    testPreprocessorWithString(
        "{{First word|Delay or suspension of an activity or a law}}",
        "<module name='String'><argument>match</argument><argument>s=Delay or suspension of an activity or a law</argument><argument>^[^%s]*</argument></module>");
    testPreprocessorWithString("{{Testcases other|{{red|CATEGORY APPLIED}}}}", "");
    testPreprocessorWithString(
        "{{safesubst:#invoke:String|match|s=Delay or suspension of an activity or a law|^[^%s]*}}",
        "<module name='String'><argument>match</argument><argument>s=Delay or suspension of an activity or a law</argument><argument>^[^%s]*</argument></module>");
    testPreprocessorWithString(
        """
            {{#switch: {{safesubst:#invoke:String|match|s=Delay or suspension of an activity or a law|^[^%s]*}}
            |c.\s
            |gTLD
            |iMac
            |iOS
            |iOS,
            |iPad
            |iPhone
            |iTunes
            |macOS
            |none
            |pH
            |pH-dependent=
            |#default=}}
            """,
        "\n");
    testPreprocessorWithString(
        "{{Short description/lowercasecheck|Delay or suspension of an activity or a law}}", "");
  }

  @Test
  void pageTypeTest() {
    testPreprocessorWithString(
        "{{pagetype |defaultns = extended |plural=y}}",
        "<module name='pagetype'><argument>main</argument></module>");
    testPreprocessorWithString(
        "{{pagetype |defaultns = all |user=exclude}}",
        "<module name='pagetype'><argument>main</argument></module>");
  }

  /**
   * Breaking down {{Main other}} from Moratorium {{Main other |{{SDcat |sd=Delay or suspension of
   * an activity or a law }} }}
   */
  @Test
  void mainOther() {
    testPreprocessorWithString("{{ns:0}}", "(Main/Article)");
    testPreprocessorWithString(
        """
            {{#if:\s
            | {{{demospace}}}    <!--Use lower case "demospace"-->
            | other\s
            }}""",
        "other");
    testPreprocessorWithString(
        "{{SDcat |sd=Delay or suspension of an activity or a law }}",
        "<module name='SDcat'><argument>setCat</argument></module>");
    testPreprocessorWithString(
        "{{Main other |{{SDcat |sd=Delay or suspension of an activity or a law }} }}", "");
    testPreprocessorWithString(
        "{{Main other|[[Category:Pages using short description with unknown parameters|_VALUE_{{PAGENAME}}]]}}",
        "");
  }

  /** More citations needed. */
  //  @Test
  //  void moreCitationsOnly() {
  //    testPreprocessorWithFile("{{more citations needed|date=April 2009}}",
  // "more_citations_needed");
  //  }
  //
  //  @Test
  //  void moreCitationsFix() {
  //    testPreprocessorWithString(
  //        "{{#if:{{{unquoted|}}}| <br /><small>{{find sources
  // mainspace|.|{{{unquoted|}}}}}</small> |{{#if:|{{#ifeq:  |none ||<br /><small>{{find sources
  // mainspace|{{{find}}} }}</small>}}|<br /><small><module name='Find sources'><argument>Find
  // sources mainspace</argument></module></small>}} }}",
  //        "");
  //  }
}
