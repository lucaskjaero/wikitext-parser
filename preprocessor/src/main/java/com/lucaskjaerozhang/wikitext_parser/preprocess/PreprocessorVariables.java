package com.lucaskjaerozhang.wikitext_parser.preprocess;

import java.util.Map;

public class PreprocessorVariables {
  private final Map<String, String> variables;

  public PreprocessorVariables(Map<String, String> variables) {
    this.variables = variables;
  }

  public String getVariable(String variableName) {
    return variables.getOrDefault(variableName, String.format("{{%s}}", variableName));
  }
}
