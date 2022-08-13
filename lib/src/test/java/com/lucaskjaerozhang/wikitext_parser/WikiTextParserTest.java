/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.objects.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WikiTextParserTest {

  @Test
  void canParseWikiTextWithoutError() {
    final String nestedSectionString =
        """
                Free floating content
                = Level one =
                Here is some content
                == Level two ==
                Here is some level two content
                == Another level two ==
                Here is more level two content
                = Level one again =
                More content""";

    final String nestedSectionXML =
        """
            <article>Free floating content
            <section title='Level one' level='1'>
            Here is some content
            <section title='Level two' level='2'>
            Here is some level two content
            </section><section title='Another level two' level='2'>
            Here is more level two content
            </section></section><section title='Level one again' level='1'>
            More content</section></article>""";

    Article result = WikiTextParser.parse(nestedSectionString);
    Assertions.assertNotNull(result);

    String parsed = WikiTextParser.writeToString(result);
    Assertions.assertEquals(nestedSectionXML, parsed);
  }
}
