package com.lucaskjaerozhang.wikitext_parser.e2e;

import com.lucaskjaerozhang.wikitext_parser.TestErrorListener;
import com.lucaskjaerozhang.wikitext_parser.WikiTextParser;
import com.lucaskjaerozhang.wikitext_parser.WikitextBaseTest;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.common.CacheFileUtils;
import com.lucaskjaerozhang.wikitext_parser.common.client.FileCachingWikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiClient;
import com.lucaskjaerozhang.wikitext_parser.common.client.WikiRestClient;
import com.lucaskjaerozhang.wikitext_parser.parse.ParseTreeBuilder;
import com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider.OnlineTemplateProvider;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.junit.jupiter.api.Assertions;

public class BaseEndToEndTest extends WikitextBaseTest {
  private static final String CACHE_DIRECTORY = "src/test/resources";

  private static final Transformer transformer;

  static {
    try {
      transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    } catch (TransformerConfigurationException e) {
      Assertions.fail(String.format("Failed to configure XML pretty printer: %s", e.getMessage()));
      throw new IllegalStateException("Failed to configure XML pretty printer", e);
    }
  }

  protected void endToEndTest(String articleName, String wiki, String language) {
    final WikiClient testClient =
        FileCachingWikiClient.builder()
            .cacheDirectory(CACHE_DIRECTORY)
            .sourceClient(WikiRestClient.builder().wiki(wiki).language(language).build())
            .build();

    String input =
        testClient
            .getPageSource(articleName)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        String.format("Failed to get article %s", articleName)))
            .getSource();

    String expected = getExpectedForArticle(articleName, wiki, language);

    WikiTextNode root =
        ParseTreeBuilder.visitTreeFromText(
            input, new OnlineTemplateProvider(testClient), List.of(new TestErrorListener()), true);
    String actual = WikiTextParser.writeToString(root);

    Assertions.assertEquals(prettyPrintXML(expected), prettyPrintXML(actual));
  }

  private static String getExpectedForArticle(String articleName, String wiki, String language) {
    CacheFileUtils.createCacheFolderStructure(CACHE_DIRECTORY, "e2e", wiki, language);
    Path path =
        Path.of(String.format("%s/e2e/%s/%s/%s.xml", CACHE_DIRECTORY, wiki, language, articleName));
    if (Files.exists(path)) {
      try {
        return Files.readString(path);
      } catch (IOException e) {
        Assertions.fail(
            String.format("Failed to open file for article: %s\n%s", e.getMessage(), path));
      }
    }
    return "";
  }

  private static String prettyPrintXML(String input) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      transformer.transform(
          new StreamSource(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))),
          new StreamResult(output));
    } catch (TransformerException e) {
      Assertions.fail(String.format("Failed to pretty print XML: %s\n%s", e.getMessage(), input));
    }
    return output.toString();
  }
}
