package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.TreeConstructionContext;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.template.TemplateEvaluator;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Map;
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
    return List.of(new NodeAttribute("name", templateName));
  }

  @Override
  public Set<String> getTemplates() {
    return Set.of(templateName);
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitTemplateWithNoParameters(this);
  }

  @Override
  public WikiTextNode rebuildWithContext(TreeConstructionContext context) {
    Optional<String> template = context.getTemplate(this.templateName);
    if (template.isEmpty()) return this;

    return new TemplateEvaluator().evaluateTemplate(template.get(), List.of(), Map.of());
  }
}
