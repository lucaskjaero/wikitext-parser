package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class MoratoriumTest extends PreprocessorEndToEndTest {
  public MoratoriumTest() {
    super("wikipedia", "en", "Moratorium_(law)");
  }

  @Test
  void moratoriumTest() {
    endToEndTest();
  }

  @Test
  void shortDescriptionTest() {
    testPreprocessorWithFile(
        "{{Short description|Delay or suspension of an activity or a law}}", "short_description");
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
  }
}
