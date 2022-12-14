package com.lucaskjaerozhang.wikitext_parser;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
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
                <section level='1' title='Level one'>
                Here is some content
                <section level='2' title='Level two'>
                Here is some level two content
                </section><section level='2' title='Another level two'>
                Here is more level two content
                </section></section><section level='1' title='Level one again'>
                More content</section></article>""";

    WikiTextNode result = (WikiTextNode) WikiTextParser.parse(nestedSectionString);
    Assertions.assertNotNull(result);

    String parsed = WikiTextParser.writeToString(result);
    Assertions.assertEquals(nestedSectionXML, parsed);

    String parsedOtherWay = WikiTextParser.parseToString(nestedSectionString);
    Assertions.assertEquals(parsed, parsedOtherWay);
  }
}
