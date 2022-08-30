package com.lucaskjaerozhang.wikitext_parser.preprocess;

import com.lucaskjaerozhang.wikitext_parser.preprocess.template.TemplateProvider;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;

public class TestTemplateProvider implements TemplateProvider {
  private Optional<String> makeTemplatePlaceholder(
      String templateName, List<String> parameterNames) {
    String parameters =
        parameterNames.stream()
            .map(p -> String.format("<parameter name='%s'>{{{%s}}}</parameter>", p, p))
            .collect(Collectors.joining(""));
    return Optional.of(
        String.format("<template name='%s'>%s</template>", templateName, parameters));
  }

  @Override
  public Optional<String> getTemplate(String template) {
    return switch (template) {
      case "Authority control" -> makeTemplatePlaceholder("Authority control", List.of());
      case "Being deleted" -> makeTemplatePlaceholder("Being deleted", List.of("1", "2", "merge"));
      case "Law-term-stub" -> makeTemplatePlaceholder("Law-term-stub", List.of());
      case "MONTHNAME" -> Optional.of("Sep");
      case "PAGENAME" -> Optional.of("PAGENAME");
      case "Reflist" -> makeTemplatePlaceholder("Reflist", List.of());
      case "Short description" -> makeTemplatePlaceholder("Short description", List.of("1"));
      case "asof" -> makeTemplatePlaceholder("asof", List.of());
      case "cite NIE" -> makeTemplatePlaceholder("cite NIE", List.of("wstitle", "year"));
      case "cite web" -> makeTemplatePlaceholder(
          "cite web", List.of("url", "title", "author", "work"));
      case "clarify" -> makeTemplatePlaceholder("clarify", List.of("text", "date"));
      case "more citations needed" -> makeTemplatePlaceholder(
          "more citations needed", List.of("date"));
      case "wiktionary" -> makeTemplatePlaceholder("wiktionary", List.of("1"));
      default -> Optional.of(
          String.format(
              "%s",
              Assertions.fail(
                  String.format("Not expecting template '%s' to be needed", template))));
    };
  }
}
