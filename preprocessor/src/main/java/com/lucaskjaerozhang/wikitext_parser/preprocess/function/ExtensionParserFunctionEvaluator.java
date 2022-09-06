package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Evaluates functions from <a
 * href="https://www.mediawiki.org/wiki/Help:Extension:ParserFunctions">Extension:ParserFunctions</a>
 */
public class ExtensionParserFunctionEvaluator extends BaseFunctionEvaluator {
  public static final String EXPRESSION = "#expr";
  public static final String IF = "#if";
  public static final String IF_EXPRESSION = "#ifexpr";
  public static final String IF_ERROR = "#iferror";
  public static final String IF_EQ = "#ifeq";

  public static String expr(List<Callable<String>> parameters) {
    checkMinParameterCount(EXPRESSION, parameters, 1);
    return String.format("<expr>%s</expr>", evaluate(parameters.get(0)));
  }

  public static String ifFunction(List<Callable<String>> parameters) {
    checkParameterCount(IF, parameters, 2, 3);
    String checked = evaluate(parameters.get(0));
    Callable<String> notEmptyValue = parameters.get(1);
    Callable<String> emptyValue = parameters.size() == 3 ? parameters.get(2) : () -> "";

    return checked.isBlank() ? evaluate(emptyValue) : evaluate(notEmptyValue);
  }

  public static String ifExpression(List<Callable<String>> parameters) {
    checkParameterCount(IF_EXPRESSION, parameters, 2, 3);
    // When actually implementing this will call expr
    String expressionValue = evaluate(parameters.get(0));
    String truthyValue = evaluate(parameters.get(1));
    String falsyValue = parameters.size() == 3 ? evaluate(parameters.get(2)) : "";

    // When actually implementing this will return the correct thing
    return String.format(
        "<ifexpr><conditional>%s</conditional><ifTrue>%s</ifTrue><ifFalse>%s</ifFalse></ifexpr>",
        expressionValue, truthyValue, falsyValue);
  }

  public static String ifError(List<Callable<String>> parameters) {
    checkParameterCount(IF_EXPRESSION, parameters, 2, 3);

    boolean errored = false;
    try {
      evaluate(parameters.get(0));
    } catch (Exception e) {
      errored = true;
    }

    Callable<String> truthyValue = parameters.get(1);
    Callable<String> falsyValue = parameters.size() == 3 ? parameters.get(2) : () -> "";

    return errored ? evaluate(truthyValue) : evaluate(falsyValue);
  }

  public static String ifEq(List<Callable<String>> parameters) {
    checkParameterCount(IF_EQ, parameters, 3, 4);
    String first = evaluate(parameters.get(0));
    String second = evaluate(parameters.get(1));

    Callable<String> equalValue = parameters.get(2);
    Callable<String> notEqualValue = parameters.size() == 4 ? parameters.get(3) : () -> "";

    return first.equals(second) ? evaluate(equalValue) : evaluate(notEqualValue);
  }
}
