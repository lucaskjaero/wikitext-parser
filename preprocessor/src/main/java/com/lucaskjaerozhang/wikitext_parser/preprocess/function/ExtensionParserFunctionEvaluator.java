package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.Optional;

/**
 * Evaluates functions from <a
 * href="https://www.mediawiki.org/wiki/Help:Extension:ParserFunctions">Extension:ParserFunctions</a>
 */
public class ExtensionParserFunctionEvaluator extends BaseFunctionEvaluator {
  public static final String EXPRESSION = "#expr";
  public static final String IF = "#if";
  public static final String IF_EXPRESSION = "#ifexpr";
  public static final String IF_EQ = "#ifeq";

  public static Optional<String> expr(List<String> parameters) {
    checkMinParameterCount(EXPRESSION, parameters, 1);
    String expressionValue = parameters.get(0).trim();

    return Optional.of(String.format("<expr>%s</expr>", expressionValue));
  }

  public static Optional<String> ifFunction(List<String> parameters) {
    checkParameterCount(IF, parameters, 2, 3);
    String checked = parameters.get(0).trim();
    String notEmptyValue = parameters.get(1);
    String emptyValue = parameters.size() == 3 ? parameters.get(2) : "";

    return Optional.of(checked.isBlank() ? emptyValue : notEmptyValue);
  }

  public static Optional<String> ifExpression(List<String> parameters) {
    checkParameterCount(IF, parameters, 2, 3);
    // When actually implementing this will call expr
    String expressionValue = parameters.get(0).trim();
    String truthyValue = parameters.get(1);
    String falsyValue = parameters.size() == 3 ? parameters.get(2) : "";

    // When actually implementing this will return the correct thing
    return Optional.of(
        String.format(
            "<ifexpr><conditional>%s</conditional><ifTrue>%s</ifTrue><ifFalse>%s</ifFalse></ifexpr>",
            expressionValue, truthyValue, falsyValue));
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
