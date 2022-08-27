package com.lucaskjaerozhang.wikitext_parser.ast.template;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.TreeConstructionContext;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextNode;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
  public List<NodeAttribute> getAttributes() {
    return List.of(new NodeAttribute("name", templateName));
  }

  @Override
  public Set<String> getTemplates() {
    return Set.of(templateName);
  }

  public Map<String, String> getNamedParameters() {
    return getChildren().stream()
        .filter(NamedTemplateParameter.class::isInstance)
        .map(NamedTemplateParameter.class::cast)
        .collect(
            Collectors.toMap(NamedTemplateParameter::getKey, NamedTemplateParameter::getValue));
  }

  public List<String> getPositionalParameters() {
    return getChildren().stream()
        .filter(PositionalTemplateParameter.class::isInstance)
        .map(PositionalTemplateParameter.class::cast)
        .map(PositionalTemplateParameter::getValue)
        .toList();
  }

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitTemplateWithParameters(this);
  }

  @Override
  public WikiTextNode rebuildWithContext(TreeConstructionContext context) {
    return this;
  }
}
