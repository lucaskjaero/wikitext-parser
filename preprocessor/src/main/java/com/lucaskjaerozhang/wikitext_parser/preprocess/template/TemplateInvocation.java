package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record TemplateInvocation(
    String templateName, List<String> positionalParameters, Map<String, String> namedParameters)
    implements Comparable<TemplateInvocation> {
  @Override
  public String toString() {
    String positional =
        positionalParameters.isEmpty()
            ? ""
            : String.format("|%s", String.join("|", positionalParameters));
    String named =
        namedParameters.isEmpty()
            ? ""
            : String.format(
                "|%s",
                namedParameters.entrySet().stream()
                    .sorted()
                    .map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
                    .collect(Collectors.joining("|")));
    return templateName.concat(positional).concat(named);
  }

  @Override
  public int compareTo(TemplateInvocation o) {
    return toString().compareTo(o.toString());
  }
}
