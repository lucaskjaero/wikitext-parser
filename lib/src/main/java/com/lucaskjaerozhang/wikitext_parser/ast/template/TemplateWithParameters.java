package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import java.util.List;

/**
 * A template with parameters<br>
 * WikiText: {{templateName|parameters}}<br>
 * XML: template
 */
public class TemplateWithParameters extends WikiTextParentNode {
  private final String templateName;

  /**
   * Creates a template with parameters.
   *
   * @param parameters The parameters of the template.
   * @param templateName The name of the template.
   */
  public TemplateWithParameters(List<WikiTextNode> parameters, String templateName) {
    super(parameters);
    this.templateName = templateName;
  }

  @Override
  protected List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("name", templateName, false));
  }

  @Override
  public String getXMLTag() {
    return "template";
  }
}
