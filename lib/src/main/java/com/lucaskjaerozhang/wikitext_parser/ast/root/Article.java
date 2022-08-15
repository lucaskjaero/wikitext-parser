package com.lucaskjaerozhang.wikitext_parser.ast.root;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
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
