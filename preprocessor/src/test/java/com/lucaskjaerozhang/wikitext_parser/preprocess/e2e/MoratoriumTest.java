package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import com.lucaskjaerozhang.wikitext_parser.grammar.preprocess.WikiTextPreprocessorLexer;
import java.util.List;
import org.junit.jupiter.api.Test;

class MoratoriumTest extends PreprocessorEndToEndTest {
  public MoratoriumTest() {
    super("wikipedia", "en", "Moratorium_(law)");
  }

  /** Short description breakdown */
  @Test
  void shortDescriptionTest() {
    testPreprocessorWithFile(
        "{{Short description|Delay or suspension of an activity or a law}}", "short_description");
  }

  @Test
  void moratoriumParagraphHeaderTemplates() {
    testPreprocessorWithString(
        """
        {{Short description|Delay or suspension of an activity or a law}}
        {{more citations needed|date=April 2009}}
        A '''moratorium''' is a delay or suspension of an activity or a law. In a [[legal]] context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.
        """,
        """
        <div class="shortdescription nomobile noexcerpt noprint searchaux" style="display:none">Delay or suspension of an activity or a law{{SHORTDESC:Delay or suspension of an activity or a law|}}</div>[[Category:<module name='pagetype'><argument>main</argument></module> with short description]]<module name='Check for unknown parameters'><argument>check</argument><argument>unknown=</argument><argument>preview=Page using [[Template:Short description]] with unknown parameter "_VALUE_"</argument><argument>ignoreblank=y</argument><argument>1</argument><argument>2</argument><argument>pagetype</argument><argument>bot</argument><argument>plural</argument></module><ifexpr><conditional><module name='String'><argument>len</argument><argument>Delay or suspension of an activity or a law</argument></module>>100</conditional><ifTrue>[[Category:<module name='pagetype'><argument>main</argument></module> with long short description]]</ifTrue><ifFalse></ifFalse></ifexpr>
        <module name='Unsubst'><argument></argument><argument>date=__DATE__</argument><argument>$B=
        <module name='Message box'><argument>ambox</argument></module></argument></module>
        A '''moratorium''' is a delay or suspension of an activity or a law. In a [[legal]] context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.
        """);
  }

  @Test
  void moratoriumParagraphConservationExample() {
    testPreprocessorWithString(
        "For example, [[animal rights]] activists and [[Conservation movement|conservation]] authorities may request fishing or hunting moratoria to protect [[endangered]] or threatened animal species.  These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.",
        "For example, [[animal rights]] activists and [[Conservation movement|conservation]] authorities may request fishing or hunting moratoria to protect [[endangered]] or threatened animal species.  These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.");
  }

  @Test
  void moratoriumParagraphClarifyTemplate() {
    testPreprocessorWithString(
        "Another instance is a delay of legal obligations or payment (''[[debt moratorium]]''). A legal official can order {{clarify|text=a delay of payment|date=December 2015}} due to extenuating circumstances, which render one party incapable of paying another.<ref>{{cite web|url=http://dictionary.reference.com/browse/moratorium?s=t|title=definition of moratorium|author=dictionary.com|work=dictionary.com}}</ref>",
        """
        Another instance is a delay of legal obligations or payment (''[[debt moratorium]]''). A legal official can order <module name='Unsubst'><argument></argument><argument>date=__DATE__</argument><argument>$B=
        <span class="cleanup-needed-content" style="padding-left:0.1em; padding-right:0.1em; color:#595959; border:1px solid #DDD;">a delay of payment</span><module name='Category handler'><argument>main</argument></module><module name='Category handler'><argument>main</argument></module><sup class="noprint Inline-Template " style="margin-left:0.1em; white-space:nowrap;">&#91;<i>[[Wikipedia:Cleanup|<span title="<module name='String'><argument>replace</argument><argument>source=Wikipedia:Cleanup</argument><argument>"</argument><argument>&quot;</argument><argument>plain=true</argument><argument>count=</argument></module><nowiki/> (December 2015)">clarification needed</span>]]</i>&#93;</sup></argument></module> due to extenuating circumstances, which render one party incapable of paying another.<ref><module name='citation/CS1'><argument>citation</argument><argument>CitationClass=web</argument></module></ref>
        """);
  }

  @Test
  void moratoriumParagraphSeeAlso() {
    testPreprocessorWithString(
        """
        ==See also==
        {{wiktionary|moratorium}}
        *[[Justice delayed is justice denied]]
        *[[2010 U.S. Deepwater Drilling Moratorium]]
        *[[Moratorium to End the War in Vietnam]]
        *[[UN moratorium on the death penalty]]
        """,
        """
        ==See also==
        <module name='Side box'><argument>main</argument></module>
        *[[Justice delayed is justice denied]]
        *[[2010 U.S. Deepwater Drilling Moratorium]]
        *[[Moratorium to End the War in Vietnam]]
        *[[UN moratorium on the death penalty]]
        """);
  }

  @Test
  void moratoriumParagraphReferences() {
    testPreprocessorWithString(
        """
        ==References==
        {{Reflist}}
        *{{cite NIE|wstitle=Moratorium|year=1905}}
        """,
        """
        ==References==
        <templatestyles src="Reflist/styles.css" /><div class="reflist  reflist-{{{group}}} " >
        <references group='' responsive='1'></references></div><module name='Check for unknown parameters'><argument>check</argument><argument>unknown=</argument><argument>preview=Page using [[Template:Reflist]] with unknown parameter "_VALUE_"</argument><argument>ignoreblank=y</argument><argument>1</argument><argument>colwidth</argument><argument>group</argument><argument>liststyle</argument><argument>refs</argument></module>
        *<module name='template wrapper'><argument>wrap</argument><argument>_template=cite encyclopedia</argument><argument>_exclude=display, inline, no-icon, noicon, short, supplement, wstitle, vb, _debug</argument><argument>_reuse=title</argument><argument>year=1905</argument><argument>encyclopedia = [[New International Encyclopedia]]</argument><argument>title=</argument><argument>url=</argument><argument>edition=1st</argument><argument>location=New York</argument><argument>publisher=Dodd, Mead</argument><argument>editor-first=D. C.</argument><argument>editor-last=Gilman</argument><argument>editor-link=Daniel Coit Gilman</argument><argument>editor2-first=H. T.</argument><argument>editor2-last=Peck</argument><argument>editor3-first=F. M.</argument><argument>editor3-last=Colby</argument></module>
        """);
  }

  @Test
  void moratoriumParagraphAuthorityControl() {
    testPreprocessorWithString(
        "{{Authority control}}",
        "<module name='Authority control'><argument>authorityControl</argument></module><module name='Check for unknown parameters'><argument>check</argument><argument>arts</argument><argument>state</argument><argument>extralist</argument><argument>ignoreblank=1</argument><argument>showblankpositional=1</argument><argument>unknown=[[Category:Pages using authority control with parameters|_VALUE_]]</argument><argument>preview=Page using [[Template:Authority control]] with \"_VALUE_\", please move this to Wikidata if possible</argument></module>");
  }

  @Test
  void moratoriumParagraphCategoriesAndStub() {
    testPreprocessorWithString(
        """
        [[Category:Legal terminology]]


        {{Law-term-stub}}
        """,
        """
        [[Category:Legal terminology]]


        <module name='Asbox'><argument>main</argument></module>
        """);
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

    // This failing is probably a lexer issue so let's test it at that level.
    testLexerWithString(
        "^[^%s]*",
        List.of(
            WikiTextPreprocessorLexer.TEXT_CHARACTER,
            WikiTextPreprocessorLexer.OPEN_SQUARE_BRACE,
            WikiTextPreprocessorLexer.TEXT_CHARACTER,
            WikiTextPreprocessorLexer.TEXT_CHARACTER,
            WikiTextPreprocessorLexer.TEXT_CHARACTER,
            WikiTextPreprocessorLexer.CLOSE_SQUARE_BRACE,
            WikiTextPreprocessorLexer.TEXT_CHARACTER,
            WikiTextPreprocessorLexer.EOF));

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
  @Test
  void moreCitationsOnly() {
    testPreprocessorWithFile("{{more citations needed|date=April 2009}}", "more_citations_needed");
  }

  /*
   * Reflist
   */

  /** Main reflist test */
  @Test
  void reflist() {
    testPreprocessorWithFile("{{Reflist}}", "reflist");
  }

  @Test
  void reflistSwitch() {
    testPreprocessorWithString(
        "{{#switch:|upper-alpha|upper-roman|lower-alpha|lower-greek|lower-roman=reflist-{{{group}}}}}",
        "reflist-{{{group}}}");
  }

  @Test
  void ambox() {
    testPreprocessorWithString(
        "{{Ambox\n| image = [[File:Question book-new.svg|50x40px|alt=]]\n}}",
        "<module name='Message box'><argument>ambox</argument></module>");
  }

  @Test
  void safesubstInvoke() {
    testPreprocessorWithString(
        "{{SAFESUBST:<noinclude />#invoke:Unsubst|}}",
        "<module name='Unsubst'><argument></argument></module>");
    testPreprocessorWithString(
        "{{SAFESUBST:<noinclude />#invoke:Unsubst||date=__DATE__ |$B=\nambox\n}}",
        "<module name='Unsubst'><argument></argument><argument>date=__DATE__</argument><argument>$B=\nambox</argument></module>");
    testPreprocessorWithString(
        "{{ safesubst:#invoke:Unsubst||date=__DATE__ |$B=payload}}",
        "<module name='Unsubst'><argument></argument><argument>date=__DATE__</argument><argument>$B=payload</argument></module>");
  }

  @Test
  void moreCitationsFix() {
    testPreprocessorWithString(
        "{{#if:| <br /><small>{{find sources mainspace|.|}}</small> |{{#if:|{{#ifeq:  |none ||<br /><small>{{find sources mainspace|{{{find}}} }}</small>}}|<br /><small><module name='Find sources'><argument>Find sources mainspace</argument></module></small>}} }}",
        "<br /><small><module name='Find sources'><argument>Find sources mainspace</argument></module></small>");
  }

  /* Whole-article fixture split into focused logical chunks. */

  @Test
  void moratoriumArticleLeadTemplates() {
    testPreprocessorWithString(
        """
        {{Short description|Delay or suspension of an activity or a law}}
        {{more citations needed|date=April 2009}}
        A '''moratorium''' is a delay or suspension of an activity or a law. In a [[legal]] context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.
        """,
        """
        <div class="shortdescription nomobile noexcerpt noprint searchaux" style="display:none">Delay or suspension of an activity or a law{{SHORTDESC:Delay or suspension of an activity or a law|}}</div>[[Category:<module name='pagetype'><argument>main</argument></module> with short description]]<module name='Check for unknown parameters'><argument>check</argument><argument>unknown=</argument><argument>preview=Page using [[Template:Short description]] with unknown parameter "_VALUE_"</argument><argument>ignoreblank=y</argument><argument>1</argument><argument>2</argument><argument>pagetype</argument><argument>bot</argument><argument>plural</argument></module><ifexpr><conditional><module name='String'><argument>len</argument><argument>Delay or suspension of an activity or a law</argument></module>>100</conditional><ifTrue>[[Category:<module name='pagetype'><argument>main</argument></module> with long short description]]</ifTrue><ifFalse></ifFalse></ifexpr>
        <module name='Unsubst'><argument></argument><argument>date=__DATE__</argument><argument>$B=
        <module name='Message box'><argument>ambox</argument></module></argument></module>
        A '''moratorium''' is a delay or suspension of an activity or a law. In a [[legal]] context, it may refer to the temporary suspension of a law to allow a legal challenge to be carried out.
        """);
  }

  @Test
  void moratoriumArticleConservationExample() {
    testPreprocessorWithString(
        """
        For example, [[animal rights]] activists and [[Conservation movement|conservation]] authorities may request fishing or hunting moratoria to protect [[endangered]] or threatened animal species.  These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.
        """,
        """
        For example, [[animal rights]] activists and [[Conservation movement|conservation]] authorities may request fishing or hunting moratoria to protect [[endangered]] or threatened animal species.  These delays, or suspensions, prevent people from hunting or fishing the animals in discussion.
        """);
  }

  @Test
  void moratoriumArticleClarifyCitation() {
    testPreprocessorWithString(
        """
        Another instance is a delay of legal obligations or payment (''[[debt moratorium]]''). A legal official can order {{clarify|text=a delay of payment|date=December 2015}} due to extenuating circumstances, which render one party incapable of paying another.<ref>{{cite web|url=http://dictionary.reference.com/browse/moratorium?s=t|title=definition of moratorium|author=dictionary.com|work=dictionary.com}}</ref>
        """,
        """
        Another instance is a delay of legal obligations or payment (''[[debt moratorium]]''). A legal official can order <module name='Unsubst'><argument></argument><argument>date=__DATE__</argument><argument>$B=
        <span class="cleanup-needed-content" style="padding-left:0.1em; padding-right:0.1em; color:#595959; border:1px solid #DDD;">a delay of payment</span><module name='Category handler'><argument>main</argument></module><module name='Category handler'><argument>main</argument></module><sup class="noprint Inline-Template " style="margin-left:0.1em; white-space:nowrap;">&#91;<i>[[Wikipedia:Cleanup|<span title="<module name='String'><argument>replace</argument><argument>source=Wikipedia:Cleanup</argument><argument>"</argument><argument>&quot;</argument><argument>plain=true</argument><argument>count=</argument></module><nowiki/> (December 2015)">clarification needed</span>]]</i>&#93;</sup></argument></module> due to extenuating circumstances, which render one party incapable of paying another.<ref><module name='citation/CS1'><argument>citation</argument><argument>CitationClass=web</argument></module></ref>
        """);
  }

  @Test
  void moratoriumArticleSeeAlso() {
    testPreprocessorWithString(
        """
        ==See also==
        {{wiktionary|moratorium}}
        *[[Justice delayed is justice denied]]
        *[[2010 U.S. Deepwater Drilling Moratorium]]
        *[[Moratorium to End the War in Vietnam]]
        *[[UN moratorium on the death penalty]]
        """,
        """
        ==See also==
        <module name='Side box'><argument>main</argument></module>
        *[[Justice delayed is justice denied]]
        *[[2010 U.S. Deepwater Drilling Moratorium]]
        *[[Moratorium to End the War in Vietnam]]
        *[[UN moratorium on the death penalty]]
        """);
  }

  @Test
  void moratoriumArticleReferences() {
    testPreprocessorWithString(
        """
        ==References==
        {{Reflist}}
        *{{cite NIE|wstitle=Moratorium|year=1905}}
        """,
        """
        ==References==
        <templatestyles src="Reflist/styles.css" /><div class="reflist  reflist-{{{group}}} " >
        <references group='' responsive='1'></references></div><module name='Check for unknown parameters'><argument>check</argument><argument>unknown=</argument><argument>preview=Page using [[Template:Reflist]] with unknown parameter "_VALUE_"</argument><argument>ignoreblank=y</argument><argument>1</argument><argument>colwidth</argument><argument>group</argument><argument>liststyle</argument><argument>refs</argument></module>
        *<module name='template wrapper'><argument>wrap</argument><argument>_template=cite encyclopedia</argument><argument>_exclude=display, inline, no-icon, noicon, short, supplement, wstitle, vb, _debug</argument><argument>_reuse=title</argument><argument>year=1905</argument><argument>encyclopedia = [[New International Encyclopedia]]</argument><argument>title=</argument><argument>url=</argument><argument>edition=1st</argument><argument>location=New York</argument><argument>publisher=Dodd, Mead</argument><argument>editor-first=D. C.</argument><argument>editor-last=Gilman</argument><argument>editor-link=Daniel Coit Gilman</argument><argument>editor2-first=H. T.</argument><argument>editor2-last=Peck</argument><argument>editor3-first=F. M.</argument><argument>editor3-last=Colby</argument></module>
        """);
  }

  @Test
  void moratoriumArticleAuthorityControl() {
    testPreprocessorWithString(
        """
        {{Authority control}}
        """,
        """
        <module name='Authority control'><argument>authorityControl</argument></module><module name='Check for unknown parameters'><argument>check</argument><argument>arts</argument><argument>state</argument><argument>extralist</argument><argument>ignoreblank=1</argument><argument>showblankpositional=1</argument><argument>unknown=[[Category:Pages using authority control with parameters|_VALUE_]]</argument><argument>preview=Page using [[Template:Authority control]] with "_VALUE_", please move this to Wikidata if possible</argument></module>
        """);
  }

  @Test
  void moratoriumArticleFooterCategoriesAndStub() {
    testPreprocessorWithString(
        """
        [[Category:Legal terminology]]

        {{Law-term-stub}}
        """,
        """
        [[Category:Legal terminology]]

        <module name='Asbox'><argument>main</argument></module>
        """);
  }

  @Test
  void moratoriumTest() {
    endToEndTest();
  }
}
