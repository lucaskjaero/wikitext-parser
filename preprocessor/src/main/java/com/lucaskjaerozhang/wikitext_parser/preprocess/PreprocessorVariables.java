package com.lucaskjaerozhang.wikitext_parser.preprocess;

import java.util.Map;
import java.util.Optional;

public class PreprocessorVariables {
  private final Map<String, String> variables;

  public PreprocessorVariables(Map<String, String> variables) {
    this.variables = variables;
  }

  public Optional<String> getVariable(String variableName) {
    return variables.containsKey(variableName)
        ? Optional.of(variables.get(variableName))
        : Optional.empty();
  }
}
