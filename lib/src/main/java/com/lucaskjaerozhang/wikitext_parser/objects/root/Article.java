package com.lucaskjaerozhang.wikitext_parser.objects.root;

import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextParentNode;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Article extends WikiTextParentNode implements WikiTextElement {
  public static final String XML_TAG = "article";

  public Article(List<WikiTextNode> content) {
    super(content);
  }

  public static Article from(List<WikiTextNode> content, CategoryList categories) {
    return new Article(
        Stream.of(List.of((WikiTextNode) categories), content)
            .flatMap(Collection::stream)
            .toList());
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
