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
  void preprocessorWorksEndToEnd() {
    final String magicPage =
        """
      {{pp-vandalism|small=yes}}
      {{Short description|Special markup for the MediaWiki software}}
      {{redirect|WP:MAGIC|WikiProject Magic|Wikipedia:WikiProject Magic}}
      {{redirect|H:MW|the MediaWiki namespace|Help:MediaWiki namespace|information about the entire MediaWiki software application|MediaWiki}}
      {{for|an introduction|Help:Magic words for beginners}}
      {{Wikipedia how-to|H:MW|WP:MAGIC|WP:MAGICWORD|WP:MAGICWORDS}}

      '''Magic words''' (including '''parser functions''', '''variables''' and '''behavior switches''') are features of [[Help:Wiki markup|wiki markup]] that give instructions to Wikipedia's underlying [[MediaWiki]] software. For example, magic words can suppress or position the table of contents, disable indexing by external search engines, and produce output dynamically based on the current page or on user-defined conditional logic. Some of these features are especially useful for [[WP:Templates|templates]].

      This page is a quick reference for magic words. For more information, refer to the main MediaWiki documentation:
      * [[mw:Help:Magic words]]: All standard magic words, including the "standard" parser functions.
      * [[mw:Help:Extension:ParserFunctions]]: Additional parser functions, including conditional expressions.

      ==General information==

      In general, there are three types of magic words.
      # '''[[#Behavior switches|Behavior switches]]''': often appear in double underscores, all uppercase, e.g., <code>__NOTOC<nowiki/>__</code>. They will change the behavior of a page, rather than return a value.
      # '''[[#Parser functions|Parser functions]]''': all in lowercase. A parser function will be followed by colon and pipe-separated parameters, e.g., <code><nowiki>{{#ifexpr:Y|Yes|No}}</nowiki></code>, wrapped in double braces. They will take a value and return a value.
      # '''[[#Variables|Variables]]''': these are all uppercase, e.g., {{tld|PAGENAME}}. A variable will be wrapped in double braces and will return a value in its place.

      The software generally interprets magic words in the following way:
      * Magic words are [[case sensitive]].
      * [[Whitespace character|White space]] is generously allowed for readability. It will be stripped from the start and end of their keywords and parameters (as is also done inside templates).
      * They can be [[WP:Transclusion|transcluded]], even variables "about the current page". This is ensured by the parsing order.
      * Instead of magically transforming into HTML instructions, {{tag|[[help:wikitext#Nowiki|nowiki]]|o}} tags remove this magic so a magic word can itself be displayed (documented), e.g. <code><nowiki><nowiki>{{#magic:}}</nowiki></nowiki></code>or <code><nowiki>{{#magic:<nowiki/>}}</nowiki></code>.

      Magic words compared to templates:
      * As with templates, magic words can be [[help:transclusion|transcluded]] and [[Help:substitution|substituted]].
      * The names of magic words are purposely chosen to be unlike the names of templates, and vice versa. Many parser function names will begin with a <code>#</code>([[Hash (character)|pound or hash]]), but template names [[wp:NCHASHTAG | will not start with a <code>#</code>]], and probably not end in a <code>:</code>(colon), or be all-uppercase.
      * The first parameter's syntax differs. In <code>{{#magic: <nowiki/>p1 | p2 | p3}}</code>, the name is <code>#magic</code>and it is followed by an unspaced <code>:</code>and a required input parameter, <code>p1</code>. With a template, <code>p1</code>is optional and it is preceded by a <code>|</code>(pipe) instead of a <code>:</code>, e.g. <code>{{template<nowiki/>|p1}}</code>.

      ==Behavior switches==
      {{further|mw:Help:Magic words#Behavior switches}}
      {| class="wikitable plainrowheaders"
      ! scope=col | Switch
      ! scope=col | Description
      |-
      ! scope=row id=TOC | <kbd><nowiki>__TOC__</nowiki></kbd>
      | Places the page's [[Help:Table of contents|table of contents (TOC)]] at the word's position.
      |-
      ! scope=row id=FORCETOC | <kbd><nowiki>__FORCETOC__</nowiki></kbd>
      | Forces the TOC to appear in its default position, even when there are fewer than four headings. Can be used anywhere on a page.
      |-
      ! scope=row id=NOTOC | <kbd><nowiki>__NOTOC__</nowiki></kbd>
      | Suppresses the appearance of the page's TOC. Can be used anywhere on a page.
      |-
      ! scope=row id=NOEDITSECTION | <kbd><nowiki>__NOEDITSECTION__</nowiki></kbd>
      | Hides the "edit" links normally beside ''all'' headings on the page. To hide the edit link beside a ''particular'' heading, specify the heading using e.g. an [[HTML element|HTML tag]] such as {{tag|h2|content=''heading''}} rather than with the usual wiki equals-signs syntax (e.g. {{nowrap|1=<kbd>== ''heading'' ==</kbd>}}{{thinsp}}).
      |-
      ! scope=row id=NEWSECTIONLINK | <kbd><nowiki>__NEWSECTIONLINK__</nowiki></kbd>
      | On non-talk pages, adds a "{{int:vector-action-addsection}}" link as a means to add a new section to the page.
      |-
      ! scope=row id=NONEWSECTIONLINK | <kbd><nowiki>__NONEWSECTIONLINK__</nowiki></kbd>
      | Removes the "{{int:vector-action-addsection}}" link (the add-new-section link) on talk pages.
      |-
      ! scope=row id=NOGALLERY | <kbd><nowiki>__NOGALLERY__</nowiki></kbd>
      | Replaces thumbnails on a category page with normal links.
      |-
      ! scope=row id=HIDDENCAT | <kbd><nowiki>__HIDDENCAT__</nowiki></kbd>
      | Makes a category [[WP:HIDDENCAT|hidden]] when included on that category's page.
      |-
      ! scope=row id=INDEX | <kbd><nowiki>__INDEX__</nowiki></kbd>
      | Instructs [[Web search engine|search engine]]s to index the page.
      |-
      ! scope=row id=NOINDEX | <kbd><nowiki>__NOINDEX__</nowiki></kbd>
      | Instructs search engines not to index the page. See [[Wikipedia:Controlling search engine indexing]].
      |-
      ! scope=row id=STATICREDIRECT | <kbd><nowiki>__STATICREDIRECT__</nowiki></kbd>
      | Prevents the link on a [[Help:Redirect|redirection]] page from being updated automatically when the page to which it redirects is moved (and "Update any redirects that point to the original title" is selected).
      |-
      ! scope=row id=DISAMBIG | <kbd><nowiki>__DISAMBIG__</nowiki></kbd>
      | Marks a page as a [[WP:DAB|disambiguation page]], adds it to [[Special:DisambiguationPages]] and places inward links in [[Special:DisambiguationPageLinks]]. (See [[mw:Extension:Disambiguator]].)
      |-
      ! scope=row id=DISPLAYTITLE | <kbd>{<nowiki/>{DISPLAYTITLE:''title''}}</kbd>
      | Used to amend the [[WP:DISPLAYTITLE|displayed form]] of the page's title.
      |-
      ! scope=row id=DEFAULTSORT | <kbd>{<nowiki/>{DEFAULTSORT:''sortkey''}}</kbd>
      | Sets the default [[WP:SORTKEY|key]] (the index) under which the page is categorised.
      |-
      ! scope=row id=NOEXTERNALLANGLINKS | <kbd>[[mw:Extension:Wikibase Client#noexternallanglinks|{<nowiki/>{NOEXTERNALLANGLINKS}}]]</kbd>
      || (equivalent to {<nowiki/>{NOEXTERNALLANGLINKS|*}}{{thinsp}}) Suppresses the automated inclusion of [[Wikidata:Help:Linking Wikipedia pages|Wikidata]] [[Help:Interlanguage links|interlanguage links]] on the lefthand side of the page. Links to particular rather than all languages may be suppressed by using {<nowiki/>{NOEXTERNALLANGLINKS|''list''}}, where ''list'' a [[Vertical bar|pipe]]-[[Delimiter|delimited]] list of [[language code]]s (e.g. {<nowiki/>{NOEXTERNALLANGLINKS|fr{{pipe}}es{{pipe}}ja}} to suppress the French ("fr"), Spanish ("es") and Japanese ("ja") interlanguage links).
      |}

      ==Variables==

      {{Shortcut|WP:VAR}}
      {{further|mw:Help:Magic words#Variables|Wikipedia:Page name#sub}}
      {{redirect|WP:PAGENAME|the article naming policy|Wikipedia:Article titles}}

      {| class="wikitable plainrowheaders"
      ! scope=col | Page name variable
      ! scope=col | Output
      ! scope=col | Description
      |-
      ! scope=row id=FULLPAGENAME | <kbd>{<nowiki/>{FULLPAGENAME}}</kbd>
      | {{FULLPAGENAME}}
      | Canonical [[Help:page name|page name]]. ''Title line''. Title unless letter-case is altered with {{tld|DISPLAYTITLE}}.
      |-
      ! scope=row id=PAGENAME | <kbd>{<nowiki/>{PAGENAME}}</kbd>
      | {{PAGENAME}}
      | Title line excluding [[Wikipedia:Namespace|namespace]].
      |-
      ! scope=row id=BASEPAGENAME | <kbd>{<nowiki/>{BASEPAGENAME}}</kbd>
      | {{BASEPAGENAME}}
      | Title of parent page, excluding namespace.
      |-
      ! scope=row id=ROOTPAGENAME | <kbd>{<nowiki/>{ROOTPAGENAME}}</kbd>
      | {{ROOTPAGENAME}}
      | Title of topmost parent (before all subpages), excluding namespace.
      |-
      ! scope=row | <kbd>[[wp:pagename#sub|{<nowiki/>{SUBPAGENAME}}]]</kbd>
      | {{SUBPAGENAME}}
      | On a subpage, rightmost portion of ''current'' title; higher subpagenames show as [[Breadcrumb navigation|backlinks]].
      |-
      ! scope=row id=ARTICLEPAGENAME | <kbd>{<nowiki/>{ARTICLEPAGENAME}}</kbd>
      | {{ARTICLEPAGENAME}}
      | rowspan=2 | Title of the subject page associated with the current page. These are useful on talk pages (but see note about Category talk pages).
      |-
      ! scope=row id=SUBJECTPAGENAME | <kbd>{<nowiki/>{SUBJECTPAGENAME}}</kbd>
      | {{SUBJECTPAGENAME}}
      |-
      ! scope=row id=TALKPAGENAME | <kbd>{<nowiki/>{TALKPAGENAME}}</kbd>
      | {{TALKPAGENAME}}
      | Title of the talk page associated with the current page. Useful on subject pages.
      |-
      ! scope=row id=NAMESPACENUMBER | <kbd>{<nowiki/>{NAMESPACENUMBER}}</kbd>
      | {{NAMESPACENUMBER:{{FULLPAGENAME}}}}
      | Number of the current page's namespace.
      |-
      ! scope=row id=NAMESPACE | <kbd>{<nowiki/>{NAMESPACE}}</kbd>
      | {{NAMESPACE}}
      | Namespace of the title.
      |-
      ! scope=row id=ARTICLESPACE | <kbd>{<nowiki/>{ARTICLESPACE}}</kbd>
      | {{ARTICLESPACE}}
      | rowspan=2 | On a talk page, the namespace part of the title of the associated subject page.
      |-
      ! scope=row id=SUBJECTSPACE | <kbd>{<nowiki/>{SUBJECTSPACE}}</kbd>
      | {{SUBJECTSPACE}}
      |-
      ! scope=row id=TALKSPACE | <kbd>{<nowiki/>{TALKSPACE}}</kbd>
      | {{TALKSPACE}}
      | Namespace of the talk page associated with the current page.
      |-
      ! scope=row id=FULLPAGENAMEE | {{longitem|style=line-height:1.5em|<kbd>{<nowiki/>{FULLPAGENAMEE}<nowiki/>}</kbd>,<br /><kbd>{<nowiki/>{PAGENAMEE}<nowiki/>}</kbd>,<br />{{pad|0.4em}}(etc.)}}
      | {{longitem|style=line-height:1.5em|{{FULLPAGENAMEE}},<br/>{{PAGENAMEE}},<br/>(etc.)}}
      | Adding an E to the end of the above variables, renders the above encoded for use in MediaWiki [[URL]]s (i.e. with underscores replacing spaces).
      |-
      ! scope=row id=SHORTDESC | <kbd>{<nowiki/>{SHORTDESC}}</kbd>
      | <!-- do not invoke this magic word here: shows nothing and places page in error-tracking category -->
      | Only works on the English Wikipedia, where it displays a short description below the article title on mobile platforms. {{crossref|printworthy=y|See [[Wikipedia:Short description]].}}
      |}

      ''Note:'' The magic words above can also take a parameter, in order to parse values on a page other than the current page. A colon (<kbd>:</kbd>) is used to pass the parameter, rather than a pipe (<kbd>{{!}}</kbd>) that is used in templates, like <kbd><nowiki>{{MAGICWORD:value}}</nowiki></kbd>. For example, <kbd><nowiki>{{TALKPAGENAME:Wikipedia:MOS}}</nowiki></kbd> returns <samp><nowiki>Wikipedia talk:MOS</nowiki></samp> on any page.

      ''Note:'' In the "Category" and "Category talk" namespaces, to wikilink (some) page name variables may require [[help:link|prefixing a colon]] to avoid unwanted categorization.

      {{crossref|printworthy=y|For more details on parser functions that relate to page names and namespaces, see: {{section link|meta:Help:Page name#Variables and parser functions}}.}}
      {| class="wikitable plainrowheaders"
      ! scope=col | Site variable
      ! scope=col | Output
      ! scope=col | Description
      |-
      ! scope=row id=SITENAME | <kbd>{<nowiki/>{SITENAME}}</kbd>
      | {{SITENAME}}
      |
      |-
      ! scope=row id=SERVER | <kbd>{<nowiki/>{SERVER}}</kbd>
      | {{SERVER}}
      |
      |-
      ! scope=row id=SERVERNAME | <kbd>{<nowiki/>{SERVERNAME}}</kbd>
      | {{SERVERNAME}}
      |
      |-
      ! scope=row id=SCRIPTPATH | <kbd>{<nowiki/>{SCRIPTPATH}}</kbd>
      | {{SCRIPTPATH}}
      |
      |-
      ! scope=row id=CURRENTVERSION | <kbd>{<nowiki/>{CURRENTVERSION}}</kbd>
      | {{CURRENTVERSION}}
      | Returns current MediaWiki version.
      |}

      ===Other variables by type===
      {| class="wikitable plainrowheaders"
      |+ Current date and time
      ! scope=col colspan=2 | Universal time
      ! scope=col colspan=2 | Local-website time
      |-
      ! scope=col | Variable
      ! scope=col | Output
      ! scope=col | Variable
      ! scope=col | Output
      |-
      ! scope=row id=CURRENTYEAR | <kbd>{<nowiki/>{CURRENTYEAR}}</kbd>
      | {{CURRENTYEAR}}
      ! scope=row id=LOCALYEAR | <kbd>{<nowiki/>{LOCALYEAR}}</kbd>
      | {{LOCALYEAR}}
      |-
      ! scope=row id=CURRENTMONTH | <kbd>{<nowiki/>{CURRENTMONTH}}</kbd>
      | {{CURRENTMONTH}}
      ! scope=row id=LOCALMONTH | <kbd>{<nowiki/>{LOCALMONTH}}</kbd>
      | {{LOCALMONTH}}
      |-
      ! scope=row id=CURRENTMONTHNAME | <kbd>{<nowiki/>{CURRENTMONTHNAME}}</kbd>
      | {{CURRENTMONTHNAME}}
      ! scope=row id=LOCALMONTHNAME | <kbd>{<nowiki/>{LOCALMONTHNAME}}</kbd>
      | {{LOCALMONTHNAME}}
      |-
      ! scope=row id=CURRENTMONTHABBREV | <kbd>{<nowiki/>{CURRENTMONTHABBREV}}</kbd>
      | {{CURRENTMONTHABBREV}}
      ! scope=row id=LOCALMONTHABBREV | <kbd>{<nowiki/>{LOCALMONTHABBREV}}</kbd>
      | {{LOCALMONTHABBREV}}
      |-
      ! scope=row id=CURRENTDAY | <kbd>{<nowiki/>{CURRENTDAY}}</kbd>{{efn|<kbd><nowiki>{{CURRENTDAY}}</nowiki></kbd>, <kbd><nowiki>{{LOCALDAY}}</nowiki></kbd>and <kbd><nowiki>{{REVISIONDAY}}</nowiki></kbd>return the day (i.e. "6"), whilst <kbd><nowiki>{{CURRENTDAY2}}</nowiki></kbd>, <kbd><nowiki>{{LOCALDAY2}}</nowiki></kbd>and <kbd><nowiki>{{REVISIONDAY2}}</nowiki></kbd>return the day with zero-padding (i.e. "06"). For all two-digit days (i.e. 10 to 31), these are the same.|name=daynote}}
      | {{CURRENTDAY}}
      ! scope=row id=LOCALDAY | <kbd>{<nowiki/>{LOCALDAY}}</kbd>{{efn|name=daynote}}
      | {{LOCALDAY}}
      |-
      ! scope=row id=CURRENTDAY2 | <kbd>{<nowiki/>{CURRENTDAY2}}</kbd>{{efn|name=daynote}}
      | {{CURRENTDAY2}}
      ! scope=row id=LOCALDAY2 | <kbd>{<nowiki/>{LOCALDAY2}}</kbd>{{efn|name=daynote}}
      | {{LOCALDAY2}}
      |-
      ! scope=row id=CURRENTDOW | <kbd>{<nowiki/>{CURRENTDOW}}</kbd>
      | {{CURRENTDOW}}
      ! scope=row id=LOCALDOW | <kbd>{<nowiki/>{LOCALDOW}}</kbd>
      | {{LOCALDOW}}
      |-
      ! scope=row id=CURRENTDAYNAME | <kbd>{<nowiki/>{CURRENTDAYNAME}}</kbd>
      | {{CURRENTDAYNAME}}
      ! scope=row id=LOCALDAYNAME | <kbd>{<nowiki/>{LOCALDAYNAME}}</kbd>
      | {{LOCALDAYNAME}}
      |-
      ! scope=row id=CURRENTTIME | <kbd>{<nowiki/>{CURRENTTIME}}</kbd>
      | {{CURRENTTIME}}
      ! scope=row id=LOCALTIME | <kbd>{<nowiki/>{LOCALTIME}}</kbd>
      | {{LOCALTIME}}
      |-
      ! scope=row id=CURRENTHOUR | <kbd>{<nowiki/>{CURRENTHOUR}}</kbd>
      | {{CURRENTHOUR}}
      ! scope=row id=LOCALHOUR | <kbd>{<nowiki/>{LOCALHOUR}}</kbd>
      | {{LOCALHOUR}}
      |-
      ! scope=row id=CURRENTWEEK | <kbd>{<nowiki/>{CURRENTWEEK}}</kbd>
      | {{CURRENTWEEK}}
      ! scope=row id=LOCALWEEK | <kbd>{<nowiki/>{LOCALWEEK}}</kbd>
      | {{LOCALWEEK}}
      |-
      ! scope=row id=CURRENTTIMESTAMP | <kbd>{<nowiki/>{CURRENTTIMESTAMP}}</kbd>
      | {{CURRENTTIMESTAMP}}
      ! scope=row id=LOCALTIMESTAMP | <kbd>{<nowiki/>{LOCALTIMESTAMP}}</kbd>
      | {{LOCALTIMESTAMP}}
      |}

      {| class="wikitable plainrowheaders"
      |+ Page revision data
      ! scope=col | Variable
      ! scope=col | Output
      |-
      ! scope=row id=REVISIONDAY | <kbd>{<nowiki/>{REVISIONDAY}}</kbd>{{efn|name=daynote}}
      | {{REVISIONDAY}}
      |-
      ! scope=row id=REVISIONDAY2 | <kbd>{<nowiki/>{REVISIONDAY2}}</kbd>{{efn|name=daynote}}
      | {{REVISIONDAY2}}
      |-
      ! scope=row id=REVISIONMONTH | <kbd>{<nowiki/>{REVISIONMONTH}}</kbd>
      | {{REVISIONMONTH}}
      |-
      ! scope=row id=REVISIONYEAR | <kbd>{<nowiki/>{REVISIONYEAR}}</kbd>
      | {{REVISIONYEAR}}
      |-
      ! scope=row id=REVISIONTIMESTAMP | <kbd>{<nowiki/>{REVISIONTIMESTAMP}}</kbd>
      | {{REVISIONTIMESTAMP}}
      |-
      ! scope=row id=REVISIONUSER | <kbd>{<nowiki/>{REVISIONUSER}}</kbd>{{efn|This shows the last user to edit the page. There is no way to show the user viewing the page with magic words due to technical restrictions.}}
      | {{REVISIONUSER}}
      |}
      {{notelist}}

      {| class="wikitable plainrowheaders"
      |+ Wiki statistics
      |-
      ! scope=row id=NUMBEROFPAGES | <kbd>{<nowiki/>{NUMBEROFPAGES}}</kbd>
      | {{NUMBEROFPAGES}}
      |-
      ! scope=row id=NUMBEROFARTICLES | <kbd>{<nowiki/>{NUMBEROFARTICLES}}</kbd>
      | {{NUMBEROFARTICLES}}
      |-
      ! scope=row id=NUMBEROFFILES | <kbd>{<nowiki/>{NUMBEROFFILES}}</kbd>
      | {{NUMBEROFFILES}}
      |-
      ! scope=row id=NUMBEROFEDITS | <kbd>{<nowiki/>{NUMBEROFEDITS}}</kbd>
      | {{NUMBEROFEDITS}}
      <!--disabled in MediaWiki (see talk page):
      |-
      ! scope=row id=NUMBEROFVIEWS | <kbd>{<nowiki/>{NUMBEROFVIEWS}}</kbd>
      | {{NUMBEROFVIEWS}}
      -->
      |-
      ! scope=row id=NUMBEROFUSERS | <kbd>{<nowiki/>{NUMBEROFUSERS}}</kbd>
      | {{NUMBEROFUSERS}}
      |-
      ! scope=row id=NUMBEROFADMINS | <kbd>{<nowiki/>{NUMBEROFADMINS}}</kbd>
      | {{NUMBEROFADMINS}}
      |-
      ! scope=row id=NUMBEROFACTIVEUSERS | <kbd>{<nowiki/>{NUMBEROFACTIVEUSERS}}</kbd>
      | {{NUMBEROFACTIVEUSERS}}
      |}

      ==Parser functions==
      {{Shortcut|WP:PF|WP:PARSER}}
      {{further|mw:Help:Magic words#Parser functions|mw:Help:Extension:ParserFunctions}}

      ===Metadata===
      {| class="wikitable plainrowheaders"
      ! scope=col | Function
      ! scope=col | Description
      |-
      ! scope=row id=PAGEID | <kbd>{<nowiki/>{PAGEID}}</kbd>
      | Unique page identifier number (for example, this page's ID is <kbd>{{PAGEID}}</kbd>).\s
      |-
      ! scope=row id=PAGESIZE | <kbd>{<nowiki/>{PAGESIZE:''fullpagename''}}</kbd>
      | Size of named page in bytes (for example, this page is <kbd>{{PAGESIZE:{{FULLPAGENAME}}}}</kbd> bytes).
      |-
      ! scope=row id=PROTECTIONLEVEL | <kbd>{<nowiki/>{PROTECTIONLEVEL:''action''{{pipe}}''fullpagename''}}</kbd>
      | [[Wikipedia:Protection policy|Protection level]] assigned to ''action'' ("edit", "move", etc.) on named page (this page's protection level for "edit" is <kbd>{{PROTECTIONLEVEL:edit|Help:Magic words}}</kbd>).
      |-
      ! scope=row id=PROTECTIONEXPIRY | <kbd>{<nowiki/>{PROTECTIONEXPIRY:''action''{{pipe}}''fullpagename''}}</kbd>
      | [[Wikipedia:Protection policy|Protection expiry]] assigned to ''action'' ("edit", "move", etc.) on named page (this page's protection expiry is <kbd>{{PROTECTIONEXPIRY:edit|Help:Magic words}}</kbd>).
      |-
      ! scope=row id=PENDINGCHANGELEVEL | <kbd>{<nowiki/>{PENDINGCHANGELEVEL}}</kbd>
      | Protection level for [[WP:PC|pending changes]] on the current page (this page, which doesn't have one, is <kbd>{{PENDINGCHANGELEVEL}}</kbd>).\s
      |-
      ! scope=row id=PAGESINCATEGORY | <kbd>{<nowiki/>{PAGESINCATEGORY:''categoryname''}}</kbd>
      | Number of pages in the category named ''categoryname''. Each subcategory is counted as one item.
      |-
      ! scope=row id=NUMBERINGROUP | <kbd>{<nowiki/>{NUMBERINGROUP:''groupname''}}</kbd>
      | Number of users in the [[Wikipedia:User access levels|user group]] named ''groupname''.
      |}
      Page IDs can be associated with articles via wikilinks (i.e. <code>[[Special:Redirect/page/3235121]]</code>goes to this page).
      To output numbers without comma [[Delimiter|separator]]s (for example, as "123456789" rather than "123,456,789"), append the parameter <kbd>|R</kbd>.

      ===Formatting===
      {{further|mw:Help:Magic words#Formatting}}
      {| class="wikitable plainrowheaders"
      ! scope=col | Function
      ! scope=col | Description
      |-
      ! scope=row id=lc | <kbd>{<nowiki/>{lc:''string''}}</kbd>
      | Converts all characters in ''string'' to lower case.
      |-
      ! scope=row id=lcfirst | <kbd>{<nowiki/>{lcfirst:''string''}}</kbd>
      | Converts first character of ''string'' to lower case.
      |-
      ! scope=row id=uc | <kbd>{<nowiki/>{uc:''string''}}</kbd>
      | Converts all characters in ''string'' to upper case.
      |-
      ! scope=row id=ucfirst | <kbd>{<nowiki/>{ucfirst:''string''}}</kbd>
      | Converts first character of ''string'' to upper case.
      |-
      ! scope=row id=formatnum | <kbd>{<nowiki/>{formatnum:''unformatted_number''}}<br>{<nowiki/>{formatnum:''formatted_num'' {{pipe}}R}}</kbd>
      | Adds comma separators to an ''unformatted_number'' (e.g. 123456789 becomes {{formatnum:123456789}}). To remove such formatting, use <kbd>{<nowiki/>{formatnum:''formatted_number''{{pipe}}R}}</kbd>(i.e. <kbd>{{braces|formatnum:7,654,321{{pipe}}R}}</kbd>, for example, produces {{formatnum:7,654,321|R}}).
      |-
      ! scope=row id=dateformat | <kbd>{<nowiki/>{#dateformat:''date''{{pipe}}''format''}}<br/>{<nowiki/>{#formatdate:''date''{{pipe}}''format''}}</kbd>
      | Formats a date according to user preferences; a default can be given as an optional case-sensitive second parameter for users without date preference; can convert a date from an existing format to any of <code>dmy</code>, <code>mdy</code>, <code>ymd</code>, or <code>[[ISO 8601]]</code>formats, with the user's preference overriding the specified format.
      |-
      ! scope=row id=padleft | <kbd>{<nowiki/>{padleft:''xyz''{{pipe}}''stringlength''}}<br/>{<nowiki/>{padright:''xyz''{{pipe}}''stringlength''}}<br><br>{<nowiki/>{padleft:''xyz''{{pipe}}''length''{{pipe}}''padstr''}}<br/>{<nowiki/>{padright:''xyz''{{pipe}}''length''{{pipe}}''padstr''}}</kbd>
      | Pad with zeroes '0' to the right or left, to fill the given length; an alternative padding string can be given as a third parameter; the repeated padding string (''padstr'') will be truncated if its length does not evenly divide the required number of characters.
      |-
      ! scope=row id=mwplural | <kbd>{<nowiki/>{plural:''N''{{pipe}}''singular''{{pipe}}''plural''}}</kbd>
      | Outputs ''singular'' if ''N'' is equal to 1, otherwise outputs ''plural''. See the [[mw:Help:Magic words#Localization|documentation at mediawiki.org]] for more details.
      |-
      ! scope=row id=mwtime | <kbd>{<nowiki/>{#time:''format''{{pipe}}''object''}}</kbd><br/><kbd>{<nowiki/>{#timel:''format''{{pipe}}''object''}}<br><br>{<nowiki/>{#time:d F Y{{pipe}}''date''{{pipe}}''langcode''}}</kbd>
      | Used to format dates and times, for ISO format, dots or English month names. <kbd>#timel</kbd>is based on local time as defined for each wiki; for English Wikipedia, this is identical to <kbd>#time</kbd>.<br />The optional 3rd parameter is the output language code (French, German, Swedish: fr, de, sv, etc.). Example Finnish: <kbd><nowiki>{{#time:d F Y|June 30, 2016|fi}}</nowiki></kbd>shows: {{#time:d F Y|June 30, 2016|fi}} (June). ISO to German: <kbd><nowiki>{{#time:d. M Y|1987-10-31|de}}</nowiki></kbd>shows: {{#time:d. M Y|1987-10-31|de}}.<br />For format codes, see: [[mw:Help:Extension:ParserFunctions##time]]. Use the format <kbd><nowiki>{{#time: H:i, j F Y (e)|...}}</nowiki></kbd>to match the format used by timestamps in signatures.
      |-
      ! scope=row id=gender | <kbd>{<nowiki/>{gender:''user''{{pipe}}''m_out''{{pipe}}''f_out''{{pipe}}''u_out''}}</kbd>
      | Outputs ''m_out'', ''f_out'' or ''u_out'' according to whether the gender specified in ''user''{{thinsp}}'s preferences is, respectively, male, female or unspecified. Other parameter permutations are available, see [[mw:Help:Magic words#gender]] and [[translatewiki:Special:MyLanguage/Gender|translatewiki:Gender]].
      |-
      ! scope=row id=mwtag | <kbd>[[mw:Help:Magic words#Miscellaneous|{<nowiki/>{#tag:''tag''{{pipe}}''content with magic''}}]]</kbd>
      | Only way to [[eval]]uate magic words ''inside a tag'', in order to generate <code>&lt;''tag''>''magic''&lt;/''tag''></code>. Also handles tag attributes.
      |}

      ===Paths===
      {| class="wikitable plainrowheaders"
      ! scope=col | Function
      ! scope=col | Description
      |-
      ! scope=row id=localurl | <kbd>{<nowiki/>{localurl:''fullpagename'' {{pipe}}''query''}}</kbd>
      | Relative [[Path (computing)|path]] to page name. The ''query'' parameter is optional.
      |-
      ! scope=row id=fullurl | <kbd>{<nowiki/>{fullurl:''fullpagename'' {{pipe}}''query''}}</kbd>
      | Absolute path, without [[Application layer|protocol prefix]] (i.e. without "{{thinsp}}<nowiki>http:</nowiki>{{thinsp}}" etc.), to page name. The ''query'' parameter is optional.
      |-
      ! scope=row id=canonicalurl | <kbd>{<nowiki/>{canonicalurl:''fullpagename'' {{pipe}}''query''}}</kbd>
      | Absolute path, including protocol prefix, to page name. The ''query'' parameter is optional.
      |-
      ! scope=row id=filepath | <kbd>{<nowiki/>{filepath:''filename''}}</kbd>
      | Absolute path to the media file ''filename''.
      |-
      ! scope=row id=urlencode | <kbd>{<nowiki/>{urlencode:''string''}}</kbd>
      | [[WP:ENCODE|Encodes]] ''string'' for use in URL query strings; <kbd>{{braces|urlencode:test string}}</kbd>, for example, produces: {{urlencode:test string}}. To encode ''string'' for use in URL paths or MediaWiki page names, append, respectively, {{para||PATH}} or {{para||WIKI}} (to produce "{{urlencode:test string|PATH}}" or "{{urlencode:test string|WIKI}}").
      |-
      ! scope=row id=anchorencode | <kbd>{<nowiki/>{anchorencode:''string''}}</kbd>
      | Input encoded for use in MediaWiki URL [[Help:Anchor|section anchor]]s.
      |-
      ! scope=row id=ns | <kbd>{<nowiki/>{ns:''n''}}</kbd>
      | Returns the name of the [[wp:Namespace|namespace]] whose index is the number ''n''. For MediaWiki URLs, use <kbd>{<nowiki/>{nse:}}</kbd>.
      |-
      ! scope=row id=rel2abs | <kbd>[[mw:Help:Extension:ParserFunctions##rel2abs|{<nowiki/>{#rel2abs:''path''}}]] </kbd>
      | Converts a relative file path to an absolute path.
      |-
      ! scope=row id=titleparts | <kbd>[[mw:Help:Extension:ParserFunctions##titleparts|{<nowiki/>{#titleparts:''fullpagename''{{pipe}}''number''{{pipe}}''first segment''}}]]</kbd>
      | Splits the fullpagename (title) into that number of segments.
      |}

      ===Conditional===
      {{further|Help:Conditional expressions}}

      {| class="wikitable plainrowheaders"
      ! scope=col | Function
      ! scope=col | Description
      |-
      ! scope=row id=expr |<kbd>[[mw:Help:Extension:Parser functions##expr|{<nowiki/>{#expr:''expression''}}]] </kbd>
      | Evaluates ''expression'' (see [[m:Help:Calculation]]).
      |-
      ! scope=row id=if | <kbd>[[mw:Help:Extension:Parser functions##if|{<nowiki/>{#if:''string'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
      | Outputs ''result2'' if ''string'' is [[Empty string|empty]], otherwise outputs ''result1''.
      |-
      ! scope=row id=ifeq | <kbd>[[mw:Help:Extension:Parser functions##ifeq|{<nowiki/>{#ifeq:''string1''{{pipe}}''string2'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
      | Outputs ''result1'' if ''string1'' and ''string2'' are equal (alphabetically or numerically), otherwise outputs ''result2''.
      |-
      ! scope=row id=iferror | <kbd>[[mw:Help:Extension:Parser functions##iferror|{<nowiki/>{#iferror:''test_string'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
      | Outputs ''result1'' if ''test_string'' generates a parsing error, otherwise outputs ''result2''.
      |-
      ! scope=row id=ifexpr | <kbd>[[mw:Help:Extension:Parser functions##ifexpr|{<nowiki/>{#ifexpr:''expression'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
      | Outputs ''result1'' if ''expression''{{thinsp}} is true, otherwise outputs ''result2''.
      |-
      ! scope=row id=ifexist | <kbd>[[mw:Help:Extension:Parser functions##ifexist|{<nowiki/>{#ifexist:''pagetitle'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
      | Outputs ''result1'' if the page [<nowiki/>[''pagetitle'']] exists, otherwise outputs ''result2''. Note that underscores are needed for spaces in namespaces.
      |-
      ! scope=row style="white-space: nowrap;" | <kbd>[[mw:Help:Extension:Parser functions##switch|{<nowiki/>{#switch:''string'' {{pipe}}''c1''=''r1'' {{pipe}}''c2''=''r2'' ... {{pipe}}''default''}}]] </kbd>
      | Outputs ''r1'' if ''string'' is ''c1'', ''r2'' if ''string'' is ''c2'', etc., otherwise outputs ''default'' (if provided).
      |}

      If, in these conditional functions, [[Empty string|empty]] unnamed parameters are to be parsed as empty rather than as text (i.e. as empty rather than as the text "{{{1}}}", "{{{2}}}", etc.), they will require trailing pipes (i.e. <kbd>{{(((}}1{{pipe}}{{)))}}</kbd>, <kbd>{{(((}}2{{pipe}}{{)))}}</kbd>, etc., rather than <kbd>{{(((}}1{{)))}}</kbd>, <kbd>{{(((}}2{{)))}}</kbd>, etc.).
      * For the use of these functions in tables, see [[Help:Conditional tables]].

      ===Other===
      {| class="wikitable plainrowheaders"
      ! scope=col | Function
      ! scope=col | Description
      |-
      ! scope=row id=babel | <kbd>[[mw:Extension:Babel#Usage|{<nowiki/>{#babel:''code1''|''code2''|...}}]]</kbd>
      | Render [[Wikipedia:Userboxes|userboxes]] telling your language skills. Improves upon {{tl|Babel}} (an alternative).
      |-
      ! scope=row id=categorytree | <kbd>[[mw:Extension:CategoryTree|{<nowiki/>{#categorytree:''category''|...}}]]</kbd>
      | List pages in a category, recursively.
      |-
      ! scope=row | <kbd>[[mw:Extension:GeoData#Parser function|{<nowiki/>{#coordinates:''arg1''|''arg2''|...}}]]</kbd>
      | Save the [[Geographic coordinate system|GeoData coordinates]] of the subject to the page's database. Used in {{tl|coord}}.
      |-
      ! scope=row style=white-space:nowrap | <kbd>[[mw:Extension:Scribunto#Usage|{<nowiki/>{#invoke:''module''|''function''|''arg1''|...}}]]</kbd>
      || Use [[mw:Extension:Scribunto|Scribunto]] to transclude a [[Wikipedia:Lua|lua]] template, e.g. function ''replace'' in [[Module:String#replace|module ''String'']].
      |-
      ! scope=row id=language | <kbd>[[mw:Help:Magic words#Miscellaneous|{<nowiki/>{#language]]:[[ISO 639|''code1''|''code2''}}]]</kbd>
      | Print the name represented by the language code, e.g. '''en''' → '''English'''. Print in language 2 if given, e.g. <kbd><nowiki>{{#language:en|zh}}</nowiki></kbd>prints {{#language:en|zh}}
      |-
      ! scope=row id=lst | <kbd>[[mw:Extension:Labeled Section Transclusion|<nowiki>{{#lst:}}, {{#lsth:}}, {{#lstx:}}</nowiki>]]</kbd>
      | Three ways to [[Help:Labeled section transclusion|transclude a section of a page]].
      |-
      ! scope=row id=mentor | <kbd>[[Wikipedia:Growth Team features|<nowiki>{{#mentor:Username}}</nowiki>]]</kbd>
      | Display the currently assigned mentor for target Username, if set.
      |-
      ! scope=row id=property | <kbd>[[m:Wikidata/Notes/Inclusion syntax v0.4|{<nowiki/>{#property:''arg1''|''arg2''|...}}]]</kbd>
      | Include a [[d:Help:FAQ#Terminology|property]] ([[Wikipedia:Wikidata|Wikidata]]) from a named entity, instead of the default on the page.
      |-
      ! scope=row id=related | <kbd>[[mw:Reading/Web/Projects/Read more|{<nowiki/>{#related:...}}]]</kbd>
      | Links to similar topics, to engage readers. (Beta feature.)
      |-
      ! scope=row id=section | <kbd>[[Help:Labeled section transclusion|{<nowiki/>{#section:}}, {<nowiki/>{#section-h:}}, {<nowiki/>{#section-x:}}]]</kbd>
      | Aliases for <kbd><nowiki>{{#lst:}}, {{#lsth}}, {{#lstx}}</nowiki></kbd>(above).
      |-
      ! scope=row id=statements | <kbd>[[d:Wikidata:How to use data on Wikimedia projects|{<nowiki/>{#statements:''arg1''|...}}]]</kbd>
      | Display the value of any statement (Wikidata) included in an item.
      |-
      ! scope=row id=target | <kbd>[[mw:Help:Extension:MassMessage#Parser function delivery lists|{<nowiki/>{#target:''fullpagename''}}]]</kbd>
      | Send a message to a list of talk pages on the fullpagename, using the [[m:MassMessage|MassMessage function]].
      |-
      ! scope=row id=int | <kbd>[[mw:Help:Magic words#Localization|{<nowiki/>{int:''pagename''}}]] </kbd>
      | [[Wikipedia:Transclusion|Transclude]] an ''interface'' message, i.e. a [[Special:PrefixIndex/MediaWiki:|pagename in MediaWiki namespace]]
      |-
      ! scope=row id=bang | <kbd>[[mw:Help:Magic_words#Other|{<nowiki/>{!}}]] </kbd>
      | Used to include a pipe character as part of a template argument or table cell contents. Before this was added as a magic word, many wikis implemented this by creating [[:Template:!]] with <code>{{!}}</code> as the content.
      |-
      ! scope=row id=equals | <kbd>[[mw:Help:Magic_words#Other|{<nowiki/>{=}}]] </kbd>
      | Used to include an equal sign as part of a template argument or table cell contents. Before this was added as a magic word, many wikis implemented this by creating [[:Template:=]] with <code>{{=}}</code> as the content.
      |}

      ==See also==
      * [[mw:Localisation]]
      * [[mw:Manual:Extending wiki markup]]
      * [https://phabricator.wikimedia.org/diffusion/MW/history/master/includes/parser/CoreParserFunctions.php CoreParserFunctions.php]
      * [[User:Cacycle/wikEd|wikEd]], a MediaWiki editor with syntax highlighting for templates and parser functions
      * {{myprefs|Gadgets|Editing|check=Syntax highlighter}}
      * {{myprefs|Beta features|check=Wiki syntax highlighting}}
      * [[Special:Version]], see last section "Parser function hooks":<!--(a #section link is not possible)-->a list that should include all of the magic words on this page
      * {{tl|Ifexist not redirect}}, works with the <nowiki>{{#ifexist:}}</nowiki>expression while allowing redirects to be identified and parsed differently

      {{Wikipedia technical help|collapsed}}

      [[Category:Wikipedia features]]

      [[he:עזרה:משתנים]]
      """;

    String expected =
        """
    {{pp-vandalism|small=yes}}
    {{Short description|Special markup for the MediaWiki software}}
    {{redirect|WP:MAGIC|WikiProject Magic|Wikipedia:WikiProject Magic}}
    {{redirect|H:MW|the MediaWiki namespace|Help:MediaWiki namespace|information about the entire MediaWiki software application|MediaWiki}}
    {{for|an introduction|Help:Magic words for beginners}}
    {{Wikipedia how-to|H:MW|WP:MAGIC|WP:MAGICWORD|WP:MAGICWORDS}}

    '''Magic words''' (including '''parser functions''', '''variables''' and '''behavior switches''') are features of [[Help:Wiki markup|wiki markup]] that give instructions to Wikipedia's underlying [[MediaWiki]] software. For example, magic words can suppress or position the table of contents, disable indexing by external search engines, and produce output dynamically based on the current page or on user-defined conditional logic. Some of these features are especially useful for [[WP:Templates|templates]].

    This page is a quick reference for magic words. For more information, refer to the main MediaWiki documentation:
    * [[mw:Help:Magic words]]: All standard magic words, including the "standard" parser functions.
    * [[mw:Help:Extension:ParserFunctions]]: Additional parser functions, including conditional expressions.

    ==General information==

    In general, there are three types of magic words.
    # '''[[#Behavior switches|Behavior switches]]''': often appear in double underscores, all uppercase, e.g., <code>__NOTOC<nowiki/>__</code>. They will change the behavior of a page, rather than return a value.
    # '''[[#Parser functions|Parser functions]]''': all in lowercase. A parser function will be followed by colon and pipe-separated parameters, e.g., <code><nowiki>{{#ifexpr:Y|Yes|No}}</nowiki></code>, wrapped in double braces. They will take a value and return a value.
    # '''[[#Variables|Variables]]''': these are all uppercase, e.g., {{tld|PAGENAME}}. A variable will be wrapped in double braces and will return a value in its place.

    The software generally interprets magic words in the following way:
    * Magic words are [[case sensitive]].
    * [[Whitespace character|White space]] is generously allowed for readability. It will be stripped from the start and end of their keywords and parameters (as is also done inside templates).
    * They can be [[WP:Transclusion|transcluded]], even variables "about the current page". This is ensured by the parsing order.
    * Instead of magically transforming into HTML instructions, {{tag|[[help:wikitext#Nowiki|nowiki]]|o}} tags remove this magic so a magic word can itself be displayed (documented), e.g. <code><nowiki><nowiki>{{#magic:}}</nowiki></nowiki></code>or <code><nowiki>{{#magic:<nowiki/>}}</nowiki></code>.

    Magic words compared to templates:
    * As with templates, magic words can be [[help:transclusion|transcluded]] and [[Help:substitution|substituted]].
    * The names of magic words are purposely chosen to be unlike the names of templates, and vice versa. Many parser function names will begin with a <code>#</code>([[Hash (character)|pound or hash]]), but template names [[wp:NCHASHTAG | will not start with a <code>#</code>]], and probably not end in a <code>:</code>(colon), or be all-uppercase.
    * The first parameter's syntax differs. In <code>{{#magic: <nowiki/>p1 | p2 | p3}}</code>, the name is <code>#magic</code>and it is followed by an unspaced <code>:</code>and a required input parameter, <code>p1</code>. With a template, <code>p1</code>is optional and it is preceded by a <code>|</code>(pipe) instead of a <code>:</code>, e.g. <code>{{template<nowiki/>|p1}}</code>.

    ==Behavior switches==
    {{further|mw:Help:Magic words#Behavior switches}}
    {| class="wikitable plainrowheaders"
    ! scope=col | Switch
    ! scope=col | Description
    |-
    ! scope=row id=TOC | <kbd><nowiki>__TOC__</nowiki></kbd>
    | Places the page's [[Help:Table of contents|table of contents (TOC)]] at the word's position.
    |-
    ! scope=row id=FORCETOC | <kbd><nowiki>__FORCETOC__</nowiki></kbd>
    | Forces the TOC to appear in its default position, even when there are fewer than four headings. Can be used anywhere on a page.
    |-
    ! scope=row id=NOTOC | <kbd><nowiki>__NOTOC__</nowiki></kbd>
    | Suppresses the appearance of the page's TOC. Can be used anywhere on a page.
    |-
    ! scope=row id=NOEDITSECTION | <kbd><nowiki>__NOEDITSECTION__</nowiki></kbd>
    | Hides the "edit" links normally beside ''all'' headings on the page. To hide the edit link beside a ''particular'' heading, specify the heading using e.g. an [[HTML element|HTML tag]] such as {{tag|h2|content=''heading''}} rather than with the usual wiki equals-signs syntax (e.g. {{nowrap|1=<kbd>== ''heading'' ==</kbd>}}{{thinsp}}).
    |-
    ! scope=row id=NEWSECTIONLINK | <kbd><nowiki>__NEWSECTIONLINK__</nowiki></kbd>
    | On non-talk pages, adds a "{{int:vector-action-addsection}}" link as a means to add a new section to the page.
    |-
    ! scope=row id=NONEWSECTIONLINK | <kbd><nowiki>__NONEWSECTIONLINK__</nowiki></kbd>
    | Removes the "{{int:vector-action-addsection}}" link (the add-new-section link) on talk pages.
    |-
    ! scope=row id=NOGALLERY | <kbd><nowiki>__NOGALLERY__</nowiki></kbd>
    | Replaces thumbnails on a category page with normal links.
    |-
    ! scope=row id=HIDDENCAT | <kbd><nowiki>__HIDDENCAT__</nowiki></kbd>
    | Makes a category [[WP:HIDDENCAT|hidden]] when included on that category's page.
    |-
    ! scope=row id=INDEX | <kbd><nowiki>__INDEX__</nowiki></kbd>
    | Instructs [[Web search engine|search engine]]s to index the page.
    |-
    ! scope=row id=NOINDEX | <kbd><nowiki>__NOINDEX__</nowiki></kbd>
    | Instructs search engines not to index the page. See [[Wikipedia:Controlling search engine indexing]].
    |-
    ! scope=row id=STATICREDIRECT | <kbd><nowiki>__STATICREDIRECT__</nowiki></kbd>
    | Prevents the link on a [[Help:Redirect|redirection]] page from being updated automatically when the page to which it redirects is moved (and "Update any redirects that point to the original title" is selected).
    |-
    ! scope=row id=DISAMBIG | <kbd><nowiki>__DISAMBIG__</nowiki></kbd>
    | Marks a page as a [[WP:DAB|disambiguation page]], adds it to [[Special:DisambiguationPages]] and places inward links in [[Special:DisambiguationPageLinks]]. (See [[mw:Extension:Disambiguator]].)
    |-
    ! scope=row id=DISPLAYTITLE | <kbd>{<nowiki/>{DISPLAYTITLE:''title''}}</kbd>
    | Used to amend the [[WP:DISPLAYTITLE|displayed form]] of the page's title.
    |-
    ! scope=row id=DEFAULTSORT | <kbd>{<nowiki/>{DEFAULTSORT:''sortkey''}}</kbd>
    | Sets the default [[WP:SORTKEY|key]] (the index) under which the page is categorised.
    |-
    ! scope=row id=NOEXTERNALLANGLINKS | <kbd>[[mw:Extension:Wikibase Client#noexternallanglinks|{<nowiki/>{NOEXTERNALLANGLINKS}}]]</kbd>
    || (equivalent to {<nowiki/>{NOEXTERNALLANGLINKS|*}}{{thinsp}}) Suppresses the automated inclusion of [[Wikidata:Help:Linking Wikipedia pages|Wikidata]] [[Help:Interlanguage links|interlanguage links]] on the lefthand side of the page. Links to particular rather than all languages may be suppressed by using {<nowiki/>{NOEXTERNALLANGLINKS|''list''}}, where ''list'' a [[Vertical bar|pipe]]-[[Delimiter|delimited]] list of [[language code]]s (e.g. {<nowiki/>{NOEXTERNALLANGLINKS|fr{{pipe}}es{{pipe}}ja}} to suppress the French ("fr"), Spanish ("es") and Japanese ("ja") interlanguage links).
    |}

    ==Variables==

    {{Shortcut|WP:VAR}}
    {{further|mw:Help:Magic words#Variables|Wikipedia:Page name#sub}}
    {{redirect|WP:PAGENAME|the article naming policy|Wikipedia:Article titles}}

    {| class="wikitable plainrowheaders"
    ! scope=col | Page name variable
    ! scope=col | Output
    ! scope=col | Description
    |-
    ! scope=row id=FULLPAGENAME | <kbd>{<nowiki/>{FULLPAGENAME}}</kbd>
    | {{FULLPAGENAME}}
    | Canonical [[Help:page name|page name]]. ''Title line''. Title unless letter-case is altered with {{tld|DISPLAYTITLE}}.
    |-
    ! scope=row id=PAGENAME | <kbd>{<nowiki/>{PAGENAME}}</kbd>
    | {{PAGENAME}}
    | Title line excluding [[Wikipedia:Namespace|namespace]].
    |-
    ! scope=row id=BASEPAGENAME | <kbd>{<nowiki/>{BASEPAGENAME}}</kbd>
    | {{BASEPAGENAME}}
    | Title of parent page, excluding namespace.
    |-
    ! scope=row id=ROOTPAGENAME | <kbd>{<nowiki/>{ROOTPAGENAME}}</kbd>
    | {{ROOTPAGENAME}}
    | Title of topmost parent (before all subpages), excluding namespace.
    |-
    ! scope=row | <kbd>[[wp:pagename#sub|{<nowiki/>{SUBPAGENAME}}]]</kbd>
    | {{SUBPAGENAME}}
    | On a subpage, rightmost portion of ''current'' title; higher subpagenames show as [[Breadcrumb navigation|backlinks]].
    |-
    ! scope=row id=ARTICLEPAGENAME | <kbd>{<nowiki/>{ARTICLEPAGENAME}}</kbd>
    | {{ARTICLEPAGENAME}}
    | rowspan=2 | Title of the subject page associated with the current page. These are useful on talk pages (but see note about Category talk pages).
    |-
    ! scope=row id=SUBJECTPAGENAME | <kbd>{<nowiki/>{SUBJECTPAGENAME}}</kbd>
    | {{SUBJECTPAGENAME}}
    |-
    ! scope=row id=TALKPAGENAME | <kbd>{<nowiki/>{TALKPAGENAME}}</kbd>
    | {{TALKPAGENAME}}
    | Title of the talk page associated with the current page. Useful on subject pages.
    |-
    ! scope=row id=NAMESPACENUMBER | <kbd>{<nowiki/>{NAMESPACENUMBER}}</kbd>
    | {{NAMESPACENUMBER:{{FULLPAGENAME}}}}
    | Number of the current page's namespace.
    |-
    ! scope=row id=NAMESPACE | <kbd>{<nowiki/>{NAMESPACE}}</kbd>
    | {{NAMESPACE}}
    | Namespace of the title.
    |-
    ! scope=row id=ARTICLESPACE | <kbd>{<nowiki/>{ARTICLESPACE}}</kbd>
    | {{ARTICLESPACE}}
    | rowspan=2 | On a talk page, the namespace part of the title of the associated subject page.
    |-
    ! scope=row id=SUBJECTSPACE | <kbd>{<nowiki/>{SUBJECTSPACE}}</kbd>
    | {{SUBJECTSPACE}}
    |-
    ! scope=row id=TALKSPACE | <kbd>{<nowiki/>{TALKSPACE}}</kbd>
    | {{TALKSPACE}}
    | Namespace of the talk page associated with the current page.
    |-
    ! scope=row id=FULLPAGENAMEE | {{longitem|style=line-height:1.5em|<kbd>{<nowiki/>{FULLPAGENAMEE}<nowiki/>}</kbd>,<br /><kbd>{<nowiki/>{PAGENAMEE}<nowiki/>}</kbd>,<br />{{pad|0.4em}}(etc.)}}
    | {{longitem|style=line-height:1.5em|{{FULLPAGENAMEE}},<br/>{{PAGENAMEE}},<br/>(etc.)}}
    | Adding an E to the end of the above variables, renders the above encoded for use in MediaWiki [[URL]]s (i.e. with underscores replacing spaces).
    |-
    ! scope=row id=SHORTDESC | <kbd>{<nowiki/>{SHORTDESC}}</kbd>
    | <!-- do not invoke this magic word here: shows nothing and places page in error-tracking category -->
    | Only works on the English Wikipedia, where it displays a short description below the article title on mobile platforms. {{crossref|printworthy=y|See [[Wikipedia:Short description]].}}
    |}

    ''Note:'' The magic words above can also take a parameter, in order to parse values on a page other than the current page. A colon (<kbd>:</kbd>) is used to pass the parameter, rather than a pipe (<kbd>{{!}}</kbd>) that is used in templates, like <kbd><nowiki>{{MAGICWORD:value}}</nowiki></kbd>. For example, <kbd><nowiki>{{TALKPAGENAME:Wikipedia:MOS}}</nowiki></kbd> returns <samp><nowiki>Wikipedia talk:MOS</nowiki></samp> on any page.

    ''Note:'' In the "Category" and "Category talk" namespaces, to wikilink (some) page name variables may require [[help:link|prefixing a colon]] to avoid unwanted categorization.

    {{crossref|printworthy=y|For more details on parser functions that relate to page names and namespaces, see: {{section link|meta:Help:Page name#Variables and parser functions}}.}}
    {| class="wikitable plainrowheaders"
    ! scope=col | Site variable
    ! scope=col | Output
    ! scope=col | Description
    |-
    ! scope=row id=SITENAME | <kbd>{<nowiki/>{SITENAME}}</kbd>
    | {{SITENAME}}
    |
    |-
    ! scope=row id=SERVER | <kbd>{<nowiki/>{SERVER}}</kbd>
    | {{SERVER}}
    |
    |-
    ! scope=row id=SERVERNAME | <kbd>{<nowiki/>{SERVERNAME}}</kbd>
    | {{SERVERNAME}}
    |
    |-
    ! scope=row id=SCRIPTPATH | <kbd>{<nowiki/>{SCRIPTPATH}}</kbd>
    | {{SCRIPTPATH}}
    |
    |-
    ! scope=row id=CURRENTVERSION | <kbd>{<nowiki/>{CURRENTVERSION}}</kbd>
    | {{CURRENTVERSION}}
    | Returns current MediaWiki version.
    |}

    ===Other variables by type===
    {| class="wikitable plainrowheaders"
    |+ Current date and time
    ! scope=col colspan=2 | Universal time
    ! scope=col colspan=2 | Local-website time
    |-
    ! scope=col | Variable
    ! scope=col | Output
    ! scope=col | Variable
    ! scope=col | Output
    |-
    ! scope=row id=CURRENTYEAR | <kbd>{<nowiki/>{CURRENTYEAR}}</kbd>
    | {{CURRENTYEAR}}
    ! scope=row id=LOCALYEAR | <kbd>{<nowiki/>{LOCALYEAR}}</kbd>
    | {{LOCALYEAR}}
    |-
    ! scope=row id=CURRENTMONTH | <kbd>{<nowiki/>{CURRENTMONTH}}</kbd>
    | {{CURRENTMONTH}}
    ! scope=row id=LOCALMONTH | <kbd>{<nowiki/>{LOCALMONTH}}</kbd>
    | {{LOCALMONTH}}
    |-
    ! scope=row id=CURRENTMONTHNAME | <kbd>{<nowiki/>{CURRENTMONTHNAME}}</kbd>
    | {{CURRENTMONTHNAME}}
    ! scope=row id=LOCALMONTHNAME | <kbd>{<nowiki/>{LOCALMONTHNAME}}</kbd>
    | {{LOCALMONTHNAME}}
    |-
    ! scope=row id=CURRENTMONTHABBREV | <kbd>{<nowiki/>{CURRENTMONTHABBREV}}</kbd>
    | {{CURRENTMONTHABBREV}}
    ! scope=row id=LOCALMONTHABBREV | <kbd>{<nowiki/>{LOCALMONTHABBREV}}</kbd>
    | {{LOCALMONTHABBREV}}
    |-
    ! scope=row id=CURRENTDAY | <kbd>{<nowiki/>{CURRENTDAY}}</kbd>{{efn|<kbd><nowiki>{{CURRENTDAY}}</nowiki></kbd>, <kbd><nowiki>{{LOCALDAY}}</nowiki></kbd>and <kbd><nowiki>{{REVISIONDAY}}</nowiki></kbd>return the day (i.e. "6"), whilst <kbd><nowiki>{{CURRENTDAY2}}</nowiki></kbd>, <kbd><nowiki>{{LOCALDAY2}}</nowiki></kbd>and <kbd><nowiki>{{REVISIONDAY2}}</nowiki></kbd>return the day with zero-padding (i.e. "06"). For all two-digit days (i.e. 10 to 31), these are the same.|name=daynote}}
    | {{CURRENTDAY}}
    ! scope=row id=LOCALDAY | <kbd>{<nowiki/>{LOCALDAY}}</kbd>{{efn|name=daynote}}
    | {{LOCALDAY}}
    |-
    ! scope=row id=CURRENTDAY2 | <kbd>{<nowiki/>{CURRENTDAY2}}</kbd>{{efn|name=daynote}}
    | {{CURRENTDAY2}}
    ! scope=row id=LOCALDAY2 | <kbd>{<nowiki/>{LOCALDAY2}}</kbd>{{efn|name=daynote}}
    | {{LOCALDAY2}}
    |-
    ! scope=row id=CURRENTDOW | <kbd>{<nowiki/>{CURRENTDOW}}</kbd>
    | {{CURRENTDOW}}
    ! scope=row id=LOCALDOW | <kbd>{<nowiki/>{LOCALDOW}}</kbd>
    | {{LOCALDOW}}
    |-
    ! scope=row id=CURRENTDAYNAME | <kbd>{<nowiki/>{CURRENTDAYNAME}}</kbd>
    | {{CURRENTDAYNAME}}
    ! scope=row id=LOCALDAYNAME | <kbd>{<nowiki/>{LOCALDAYNAME}}</kbd>
    | {{LOCALDAYNAME}}
    |-
    ! scope=row id=CURRENTTIME | <kbd>{<nowiki/>{CURRENTTIME}}</kbd>
    | {{CURRENTTIME}}
    ! scope=row id=LOCALTIME | <kbd>{<nowiki/>{LOCALTIME}}</kbd>
    | {{LOCALTIME}}
    |-
    ! scope=row id=CURRENTHOUR | <kbd>{<nowiki/>{CURRENTHOUR}}</kbd>
    | {{CURRENTHOUR}}
    ! scope=row id=LOCALHOUR | <kbd>{<nowiki/>{LOCALHOUR}}</kbd>
    | {{LOCALHOUR}}
    |-
    ! scope=row id=CURRENTWEEK | <kbd>{<nowiki/>{CURRENTWEEK}}</kbd>
    | {{CURRENTWEEK}}
    ! scope=row id=LOCALWEEK | <kbd>{<nowiki/>{LOCALWEEK}}</kbd>
    | {{LOCALWEEK}}
    |-
    ! scope=row id=CURRENTTIMESTAMP | <kbd>{<nowiki/>{CURRENTTIMESTAMP}}</kbd>
    | {{CURRENTTIMESTAMP}}
    ! scope=row id=LOCALTIMESTAMP | <kbd>{<nowiki/>{LOCALTIMESTAMP}}</kbd>
    | {{LOCALTIMESTAMP}}
    |}

    {| class="wikitable plainrowheaders"
    |+ Page revision data
    ! scope=col | Variable
    ! scope=col | Output
    |-
    ! scope=row id=REVISIONDAY | <kbd>{<nowiki/>{REVISIONDAY}}</kbd>{{efn|name=daynote}}
    | {{REVISIONDAY}}
    |-
    ! scope=row id=REVISIONDAY2 | <kbd>{<nowiki/>{REVISIONDAY2}}</kbd>{{efn|name=daynote}}
    | {{REVISIONDAY2}}
    |-
    ! scope=row id=REVISIONMONTH | <kbd>{<nowiki/>{REVISIONMONTH}}</kbd>
    | {{REVISIONMONTH}}
    |-
    ! scope=row id=REVISIONYEAR | <kbd>{<nowiki/>{REVISIONYEAR}}</kbd>
    | {{REVISIONYEAR}}
    |-
    ! scope=row id=REVISIONTIMESTAMP | <kbd>{<nowiki/>{REVISIONTIMESTAMP}}</kbd>
    | {{REVISIONTIMESTAMP}}
    |-
    ! scope=row id=REVISIONUSER | <kbd>{<nowiki/>{REVISIONUSER}}</kbd>{{efn|This shows the last user to edit the page. There is no way to show the user viewing the page with magic words due to technical restrictions.}}
    | {{REVISIONUSER}}
    |}
    {{notelist}}

    {| class="wikitable plainrowheaders"
    |+ Wiki statistics
    |-
    ! scope=row id=NUMBEROFPAGES | <kbd>{<nowiki/>{NUMBEROFPAGES}}</kbd>
    | {{NUMBEROFPAGES}}
    |-
    ! scope=row id=NUMBEROFARTICLES | <kbd>{<nowiki/>{NUMBEROFARTICLES}}</kbd>
    | {{NUMBEROFARTICLES}}
    |-
    ! scope=row id=NUMBEROFFILES | <kbd>{<nowiki/>{NUMBEROFFILES}}</kbd>
    | {{NUMBEROFFILES}}
    |-
    ! scope=row id=NUMBEROFEDITS | <kbd>{<nowiki/>{NUMBEROFEDITS}}</kbd>
    | {{NUMBEROFEDITS}}
    <!--disabled in MediaWiki (see talk page):
    |-
    ! scope=row id=NUMBEROFVIEWS | <kbd>{<nowiki/>{NUMBEROFVIEWS}}</kbd>
    | {{NUMBEROFVIEWS}}
    -->
    |-
    ! scope=row id=NUMBEROFUSERS | <kbd>{<nowiki/>{NUMBEROFUSERS}}</kbd>
    | {{NUMBEROFUSERS}}
    |-
    ! scope=row id=NUMBEROFADMINS | <kbd>{<nowiki/>{NUMBEROFADMINS}}</kbd>
    | {{NUMBEROFADMINS}}
    |-
    ! scope=row id=NUMBEROFACTIVEUSERS | <kbd>{<nowiki/>{NUMBEROFACTIVEUSERS}}</kbd>
    | {{NUMBEROFACTIVEUSERS}}
    |}

    ==Parser functions==
    {{Shortcut|WP:PF|WP:PARSER}}
    {{further|mw:Help:Magic words#Parser functions|mw:Help:Extension:ParserFunctions}}

    ===Metadata===
    {| class="wikitable plainrowheaders"
    ! scope=col | Function
    ! scope=col | Description
    |-
    ! scope=row id=PAGEID | <kbd>{<nowiki/>{PAGEID}}</kbd>
    | Unique page identifier number (for example, this page's ID is <kbd>{{PAGEID}}</kbd>).\s
    |-
    ! scope=row id=PAGESIZE | <kbd>{<nowiki/>{PAGESIZE:''fullpagename''}}</kbd>
    | Size of named page in bytes (for example, this page is <kbd>{{PAGESIZE:{{FULLPAGENAME}}}}</kbd> bytes).
    |-
    ! scope=row id=PROTECTIONLEVEL | <kbd>{<nowiki/>{PROTECTIONLEVEL:''action''{{pipe}}''fullpagename''}}</kbd>
    | [[Wikipedia:Protection policy|Protection level]] assigned to ''action'' ("edit", "move", etc.) on named page (this page's protection level for "edit" is <kbd>{{PROTECTIONLEVEL:edit|Help:Magic words}}</kbd>).
    |-
    ! scope=row id=PROTECTIONEXPIRY | <kbd>{<nowiki/>{PROTECTIONEXPIRY:''action''{{pipe}}''fullpagename''}}</kbd>
    | [[Wikipedia:Protection policy|Protection expiry]] assigned to ''action'' ("edit", "move", etc.) on named page (this page's protection expiry is <kbd>{{PROTECTIONEXPIRY:edit|Help:Magic words}}</kbd>).
    |-
    ! scope=row id=PENDINGCHANGELEVEL | <kbd>{<nowiki/>{PENDINGCHANGELEVEL}}</kbd>
    | Protection level for [[WP:PC|pending changes]] on the current page (this page, which doesn't have one, is <kbd>{{PENDINGCHANGELEVEL}}</kbd>).\s
    |-
    ! scope=row id=PAGESINCATEGORY | <kbd>{<nowiki/>{PAGESINCATEGORY:''categoryname''}}</kbd>
    | Number of pages in the category named ''categoryname''. Each subcategory is counted as one item.
    |-
    ! scope=row id=NUMBERINGROUP | <kbd>{<nowiki/>{NUMBERINGROUP:''groupname''}}</kbd>
    | Number of users in the [[Wikipedia:User access levels|user group]] named ''groupname''.
    |}
    Page IDs can be associated with articles via wikilinks (i.e. <code>[[Special:Redirect/page/3235121]]</code>goes to this page).
    To output numbers without comma [[Delimiter|separator]]s (for example, as "123456789" rather than "123,456,789"), append the parameter <kbd>|R</kbd>.

    ===Formatting===
    {{further|mw:Help:Magic words#Formatting}}
    {| class="wikitable plainrowheaders"
    ! scope=col | Function
    ! scope=col | Description
    |-
    ! scope=row id=lc | <kbd>{<nowiki/>{lc:''string''}}</kbd>
    | Converts all characters in ''string'' to lower case.
    |-
    ! scope=row id=lcfirst | <kbd>{<nowiki/>{lcfirst:''string''}}</kbd>
    | Converts first character of ''string'' to lower case.
    |-
    ! scope=row id=uc | <kbd>{<nowiki/>{uc:''string''}}</kbd>
    | Converts all characters in ''string'' to upper case.
    |-
    ! scope=row id=ucfirst | <kbd>{<nowiki/>{ucfirst:''string''}}</kbd>
    | Converts first character of ''string'' to upper case.
    |-
    ! scope=row id=formatnum | <kbd>{<nowiki/>{formatnum:''unformatted_number''}}<br>{<nowiki/>{formatnum:''formatted_num'' {{pipe}}R}}</kbd>
    | Adds comma separators to an ''unformatted_number'' (e.g. 123456789 becomes {{formatnum:123456789}}). To remove such formatting, use <kbd>{<nowiki/>{formatnum:''formatted_number''{{pipe}}R}}</kbd>(i.e. <kbd>{{braces|formatnum:7,654,321{{pipe}}R}}</kbd>, for example, produces {{formatnum:7,654,321|R}}).
    |-
    ! scope=row id=dateformat | <kbd>{<nowiki/>{#dateformat:''date''{{pipe}}''format''}}<br/>{<nowiki/>{#formatdate:''date''{{pipe}}''format''}}</kbd>
    | Formats a date according to user preferences; a default can be given as an optional case-sensitive second parameter for users without date preference; can convert a date from an existing format to any of <code>dmy</code>, <code>mdy</code>, <code>ymd</code>, or <code>[[ISO 8601]]</code>formats, with the user's preference overriding the specified format.
    |-
    ! scope=row id=padleft | <kbd>{<nowiki/>{padleft:''xyz''{{pipe}}''stringlength''}}<br/>{<nowiki/>{padright:''xyz''{{pipe}}''stringlength''}}<br><br>{<nowiki/>{padleft:''xyz''{{pipe}}''length''{{pipe}}''padstr''}}<br/>{<nowiki/>{padright:''xyz''{{pipe}}''length''{{pipe}}''padstr''}}</kbd>
    | Pad with zeroes '0' to the right or left, to fill the given length; an alternative padding string can be given as a third parameter; the repeated padding string (''padstr'') will be truncated if its length does not evenly divide the required number of characters.
    |-
    ! scope=row id=mwplural | <kbd>{<nowiki/>{plural:''N''{{pipe}}''singular''{{pipe}}''plural''}}</kbd>
    | Outputs ''singular'' if ''N'' is equal to 1, otherwise outputs ''plural''. See the [[mw:Help:Magic words#Localization|documentation at mediawiki.org]] for more details.
    |-
    ! scope=row id=mwtime | <kbd>{<nowiki/>{#time:''format''{{pipe}}''object''}}</kbd><br/><kbd>{<nowiki/>{#timel:''format''{{pipe}}''object''}}<br><br>{<nowiki/>{#time:d F Y{{pipe}}''date''{{pipe}}''langcode''}}</kbd>
    | Used to format dates and times, for ISO format, dots or English month names. <kbd>#timel</kbd>is based on local time as defined for each wiki; for English Wikipedia, this is identical to <kbd>#time</kbd>.<br />The optional 3rd parameter is the output language code (French, German, Swedish: fr, de, sv, etc.). Example Finnish: <kbd><nowiki>{{#time:d F Y|June 30, 2016|fi}}</nowiki></kbd>shows: {{#time:d F Y|June 30, 2016|fi}} (June). ISO to German: <kbd><nowiki>{{#time:d. M Y|1987-10-31|de}}</nowiki></kbd>shows: {{#time:d. M Y|1987-10-31|de}}.<br />For format codes, see: [[mw:Help:Extension:ParserFunctions##time]]. Use the format <kbd><nowiki>{{#time: H:i, j F Y (e)|...}}</nowiki></kbd>to match the format used by timestamps in signatures.
    |-
    ! scope=row id=gender | <kbd>{<nowiki/>{gender:''user''{{pipe}}''m_out''{{pipe}}''f_out''{{pipe}}''u_out''}}</kbd>
    | Outputs ''m_out'', ''f_out'' or ''u_out'' according to whether the gender specified in ''user''{{thinsp}}'s preferences is, respectively, male, female or unspecified. Other parameter permutations are available, see [[mw:Help:Magic words#gender]] and [[translatewiki:Special:MyLanguage/Gender|translatewiki:Gender]].
    |-
    ! scope=row id=mwtag | <kbd>[[mw:Help:Magic words#Miscellaneous|{<nowiki/>{#tag:''tag''{{pipe}}''content with magic''}}]]</kbd>
    | Only way to [[eval]]uate magic words ''inside a tag'', in order to generate <code>&lt;''tag''>''magic''&lt;/''tag''></code>. Also handles tag attributes.
    |}

    ===Paths===
    {| class="wikitable plainrowheaders"
    ! scope=col | Function
    ! scope=col | Description
    |-
    ! scope=row id=localurl | <kbd>{<nowiki/>{localurl:''fullpagename'' {{pipe}}''query''}}</kbd>
    | Relative [[Path (computing)|path]] to page name. The ''query'' parameter is optional.
    |-
    ! scope=row id=fullurl | <kbd>{<nowiki/>{fullurl:''fullpagename'' {{pipe}}''query''}}</kbd>
    | Absolute path, without [[Application layer|protocol prefix]] (i.e. without "{{thinsp}}<nowiki>http:</nowiki>{{thinsp}}" etc.), to page name. The ''query'' parameter is optional.
    |-
    ! scope=row id=canonicalurl | <kbd>{<nowiki/>{canonicalurl:''fullpagename'' {{pipe}}''query''}}</kbd>
    | Absolute path, including protocol prefix, to page name. The ''query'' parameter is optional.
    |-
    ! scope=row id=filepath | <kbd>{<nowiki/>{filepath:''filename''}}</kbd>
    | Absolute path to the media file ''filename''.
    |-
    ! scope=row id=urlencode | <kbd>{<nowiki/>{urlencode:''string''}}</kbd>
    | [[WP:ENCODE|Encodes]] ''string'' for use in URL query strings; <kbd>{{braces|urlencode:test string}}</kbd>, for example, produces: {{urlencode:test string}}. To encode ''string'' for use in URL paths or MediaWiki page names, append, respectively, {{para||PATH}} or {{para||WIKI}} (to produce "{{urlencode:test string|PATH}}" or "{{urlencode:test string|WIKI}}").
    |-
    ! scope=row id=anchorencode | <kbd>{<nowiki/>{anchorencode:''string''}}</kbd>
    | Input encoded for use in MediaWiki URL [[Help:Anchor|section anchor]]s.
    |-
    ! scope=row id=ns | <kbd>{<nowiki/>{ns:''n''}}</kbd>
    | Returns the name of the [[wp:Namespace|namespace]] whose index is the number ''n''. For MediaWiki URLs, use <kbd>{<nowiki/>{nse:}}</kbd>.
    |-
    ! scope=row id=rel2abs | <kbd>[[mw:Help:Extension:ParserFunctions##rel2abs|{<nowiki/>{#rel2abs:''path''}}]] </kbd>
    | Converts a relative file path to an absolute path.
    |-
    ! scope=row id=titleparts | <kbd>[[mw:Help:Extension:ParserFunctions##titleparts|{<nowiki/>{#titleparts:''fullpagename''{{pipe}}''number''{{pipe}}''first segment''}}]]</kbd>
    | Splits the fullpagename (title) into that number of segments.
    |}

    ===Conditional===
    {{further|Help:Conditional expressions}}

    {| class="wikitable plainrowheaders"
    ! scope=col | Function
    ! scope=col | Description
    |-
    ! scope=row id=expr |<kbd>[[mw:Help:Extension:Parser functions##expr|{<nowiki/>{#expr:''expression''}}]] </kbd>
    | Evaluates ''expression'' (see [[m:Help:Calculation]]).
    |-
    ! scope=row id=if | <kbd>[[mw:Help:Extension:Parser functions##if|{<nowiki/>{#if:''string'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
    | Outputs ''result2'' if ''string'' is [[Empty string|empty]], otherwise outputs ''result1''.
    |-
    ! scope=row id=ifeq | <kbd>[[mw:Help:Extension:Parser functions##ifeq|{<nowiki/>{#ifeq:''string1''{{pipe}}''string2'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
    | Outputs ''result1'' if ''string1'' and ''string2'' are equal (alphabetically or numerically), otherwise outputs ''result2''.
    |-
    ! scope=row id=iferror | <kbd>[[mw:Help:Extension:Parser functions##iferror|{<nowiki/>{#iferror:''test_string'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
    | Outputs ''result1'' if ''test_string'' generates a parsing error, otherwise outputs ''result2''.
    |-
    ! scope=row id=ifexpr | <kbd>[[mw:Help:Extension:Parser functions##ifexpr|{<nowiki/>{#ifexpr:''expression'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
    | Outputs ''result1'' if ''expression''{{thinsp}} is true, otherwise outputs ''result2''.
    |-
    ! scope=row id=ifexist | <kbd>[[mw:Help:Extension:Parser functions##ifexist|{<nowiki/>{#ifexist:''pagetitle'' {{pipe}}''result1'' {{pipe}}''result2''}}]] </kbd>
    | Outputs ''result1'' if the page [<nowiki/>[''pagetitle'']] exists, otherwise outputs ''result2''. Note that underscores are needed for spaces in namespaces.
    |-
    ! scope=row style="white-space: nowrap;" | <kbd>[[mw:Help:Extension:Parser functions##switch|{<nowiki/>{#switch:''string'' {{pipe}}''c1''=''r1'' {{pipe}}''c2''=''r2'' ... {{pipe}}''default''}}]] </kbd>
    | Outputs ''r1'' if ''string'' is ''c1'', ''r2'' if ''string'' is ''c2'', etc., otherwise outputs ''default'' (if provided).
    |}

    If, in these conditional functions, [[Empty string|empty]] unnamed parameters are to be parsed as empty rather than as text (i.e. as empty rather than as the text "{{{1}}}", "{{{2}}}", etc.), they will require trailing pipes (i.e. <kbd>{{(((}}1{{pipe}}{{)))}}</kbd>, <kbd>{{(((}}2{{pipe}}{{)))}}</kbd>, etc., rather than <kbd>{{(((}}1{{)))}}</kbd>, <kbd>{{(((}}2{{)))}}</kbd>, etc.).
    * For the use of these functions in tables, see [[Help:Conditional tables]].

    ===Other===
    {| class="wikitable plainrowheaders"
    ! scope=col | Function
    ! scope=col | Description
    |-
    ! scope=row id=babel | <kbd>[[mw:Extension:Babel#Usage|{<nowiki/>{#babel:''code1''|''code2''|...}}]]</kbd>
    | Render [[Wikipedia:Userboxes|userboxes]] telling your language skills. Improves upon {{tl|Babel}} (an alternative).
    |-
    ! scope=row id=categorytree | <kbd>[[mw:Extension:CategoryTree|{<nowiki/>{#categorytree:''category''|...}}]]</kbd>
    | List pages in a category, recursively.
    |-
    ! scope=row | <kbd>[[mw:Extension:GeoData#Parser function|{<nowiki/>{#coordinates:''arg1''|''arg2''|...}}]]</kbd>
    | Save the [[Geographic coordinate system|GeoData coordinates]] of the subject to the page's database. Used in {{tl|coord}}.
    |-
    ! scope=row style=white-space:nowrap | <kbd>[[mw:Extension:Scribunto#Usage|{<nowiki/>{#invoke:''module''|''function''|''arg1''|...}}]]</kbd>
    || Use [[mw:Extension:Scribunto|Scribunto]] to transclude a [[Wikipedia:Lua|lua]] template, e.g. function ''replace'' in [[Module:String#replace|module ''String'']].
    |-
    ! scope=row id=language | <kbd>[[mw:Help:Magic words#Miscellaneous|{<nowiki/>{#language]]:[[ISO 639|''code1''|''code2''}}]]</kbd>
    | Print the name represented by the language code, e.g. '''en''' → '''English'''. Print in language 2 if given, e.g. <kbd><nowiki>{{#language:en|zh}}</nowiki></kbd>prints {{#language:en|zh}}
    |-
    ! scope=row id=lst | <kbd>[[mw:Extension:Labeled Section Transclusion|<nowiki>{{#lst:}}, {{#lsth:}}, {{#lstx:}}</nowiki>]]</kbd>
    | Three ways to [[Help:Labeled section transclusion|transclude a section of a page]].
    |-
    ! scope=row id=mentor | <kbd>[[Wikipedia:Growth Team features|<nowiki>{{#mentor:Username}}</nowiki>]]</kbd>
    | Display the currently assigned mentor for target Username, if set.
    |-
    ! scope=row id=property | <kbd>[[m:Wikidata/Notes/Inclusion syntax v0.4|{<nowiki/>{#property:''arg1''|''arg2''|...}}]]</kbd>
    | Include a [[d:Help:FAQ#Terminology|property]] ([[Wikipedia:Wikidata|Wikidata]]) from a named entity, instead of the default on the page.
    |-
    ! scope=row id=related | <kbd>[[mw:Reading/Web/Projects/Read more|{<nowiki/>{#related:...}}]]</kbd>
    | Links to similar topics, to engage readers. (Beta feature.)
    |-
    ! scope=row id=section | <kbd>[[Help:Labeled section transclusion|{<nowiki/>{#section:}}, {<nowiki/>{#section-h:}}, {<nowiki/>{#section-x:}}]]</kbd>
    | Aliases for <kbd><nowiki>{{#lst:}}, {{#lsth}}, {{#lstx}}</nowiki></kbd>(above).
    |-
    ! scope=row id=statements | <kbd>[[d:Wikidata:How to use data on Wikimedia projects|{<nowiki/>{#statements:''arg1''|...}}]]</kbd>
    | Display the value of any statement (Wikidata) included in an item.
    |-
    ! scope=row id=target | <kbd>[[mw:Help:Extension:MassMessage#Parser function delivery lists|{<nowiki/>{#target:''fullpagename''}}]]</kbd>
    | Send a message to a list of talk pages on the fullpagename, using the [[m:MassMessage|MassMessage function]].
    |-
    ! scope=row id=int | <kbd>[[mw:Help:Magic words#Localization|{<nowiki/>{int:''pagename''}}]] </kbd>
    | [[Wikipedia:Transclusion|Transclude]] an ''interface'' message, i.e. a [[Special:PrefixIndex/MediaWiki:|pagename in MediaWiki namespace]]
    |-
    ! scope=row id=bang | <kbd>[[mw:Help:Magic_words#Other|{<nowiki/>{!}}]] </kbd>
    | Used to include a pipe character as part of a template argument or table cell contents. Before this was added as a magic word, many wikis implemented this by creating [[:Template:!]] with <code>{{!}}</code> as the content.
    |-
    ! scope=row id=equals | <kbd>[[mw:Help:Magic_words#Other|{<nowiki/>{=}}]] </kbd>
    | Used to include an equal sign as part of a template argument or table cell contents. Before this was added as a magic word, many wikis implemented this by creating [[:Template:=]] with <code>{{=}}</code> as the content.
    |}

    ==See also==
    * [[mw:Localisation]]
    * [[mw:Manual:Extending wiki markup]]
    * [https://phabricator.wikimedia.org/diffusion/MW/history/master/includes/parser/CoreParserFunctions.php CoreParserFunctions.php]
    * [[User:Cacycle/wikEd|wikEd]], a MediaWiki editor with syntax highlighting for templates and parser functions
    * {{myprefs|Gadgets|Editing|check=Syntax highlighter}}
    * {{myprefs|Beta features|check=Wiki syntax highlighting}}
    * [[Special:Version]], see last section "Parser function hooks":<!--(a #section link is not possible)-->a list that should include all of the magic words on this page
    * {{tl|Ifexist not redirect}}, works with the <nowiki>{{#ifexist:}}</nowiki>expression while allowing redirects to be identified and parsed differently

    {{Wikipedia technical help|collapsed}}

    [[Category:Wikipedia features]]

    [[he:עזרה:משתנים]]
    """;

    testPreprocessor(magicPage, expected);
  }
}
