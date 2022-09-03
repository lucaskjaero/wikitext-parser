package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.Optional;

/**
 * Evaluates functions from <a
 * href="https://www.mediawiki.org/wiki/Help:Extension:ParserFunctions">Extension:ParserFunctions</a>
 */
public class ExtensionParserFunctionEvaluator extends BaseFunctionEvaluator {
  public static final String IF = "#if";
  public static final String IF_EQ = "#ifeq";

  public static Optional<String> ifFunction(List<String> parameters) {
    checkParameterCount(IF, parameters, 2, 3);
    String checked = parameters.get(0).trim();
    String notEmptyValue = parameters.get(1);
    String emptyValue = parameters.size() == 3 ? parameters.get(2) : "";

    return Optional.of(checked.isBlank() ? emptyValue : notEmptyValue);
  }

  public static Optional<String> ifEq(List<String> parameters) {
    checkParameterCount(IF_EQ, parameters, 3, 4);
    String first = parameters.get(0).trim();
    String second = parameters.get(1).trim();
    String equalValue = parameters.get(2);
    String notEqualValue = parameters.size() == 4 ? parameters.get(3) : "";

    return Optional.of(first.equals(second) ? equalValue : notEqualValue);
  }
}
