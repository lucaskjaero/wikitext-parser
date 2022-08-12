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

    Article result = WikiTextParser.parse(nestedSectionString);
    Assertions.assertNotNull(result);

    String parsed = WikiTextParser.writeToString(result);
    Assertions.assertEquals("", parsed);
  }
}
