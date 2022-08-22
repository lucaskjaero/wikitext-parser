package com.lucaskjaerozhang.wikitext_parser.ast.template.invocation;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A template without any parameters<br>
 * WikiText: {{templateName}}<br>
 * XML: template
 */
public class TemplateWithNoParameters extends WikiTextNode {
  private final String templateName;

  /**
   * Creates a template without parameters.
   *
   * @param templateName The name of the template.
   */
  public TemplateWithNoParameters(String templateName) {
    this.templateName = templateName;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("name", templateName, false));
  }

  @Override
  public Set<String> getTemplates() {
    return Set.of(templateName);
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitTemplateWithNoParameters(this);
  }
}
