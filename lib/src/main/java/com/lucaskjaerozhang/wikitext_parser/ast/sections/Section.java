package com.lucaskjaerozhang.wikitext_parser.ast.sections;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextElement;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import java.util.List;

/**
 * The contents of a section.<br>
 * WikiText: ## Section title ##\n content<br>
 * XML: section<br>
 */
public class Section extends WikiTextParentNode implements WikiTextElement {
  public static final String XML_TAG = "section";
  public static final String TITLE_ATTRIBUTE = "title";
  public static final String LEVEL_ATTRIBUTE = "level";

  private final String title;
  private final Integer level;

  /**
   * Creates a section.
   *
   * @param title The section title
   * @param level The size of the section, from 1-6.
   * @param content The contents of the section.
   */
  public Section(String title, Integer level, List<WikiTextNode> content) {
    super(content);
    this.title = title.trim();
    this.level = level;
  }

  @Override
  public String getXMLTag() {
    return XML_TAG;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(
        new NodeAttribute(TITLE_ATTRIBUTE, title, false),
        new NodeAttribute(LEVEL_ATTRIBUTE, level.toString(), false));
  }
}
