package com.lucaskjaerozhang.wikitext_parser.preprocess;

import java.util.Map;

public class PreprocessorVariables {
  private Map<String, String> variables;

  public PreprocessorVariables(Map<String, String> variables) {
    this.variables = variables;
  }

  public String getVariable(String variableName) {
    return variables.getOrDefault(variableName, variableName);
  }
}
