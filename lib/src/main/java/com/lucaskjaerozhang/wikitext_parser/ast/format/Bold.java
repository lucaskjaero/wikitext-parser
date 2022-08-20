package com.lucaskjaerozhang.wikitext_parser.ast.format;

import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import java.util.List;

/**
 * Bolded text.<br>
 * WikiText: '''text'''<br>
 * XML: bold
 */
public class Bold extends WikiTextParentNode implements WikiTextElement {
  public static final String XML_TAG = "bold";

  public Bold(List<WikiTextNode> content) {
    super(content);
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }
}
