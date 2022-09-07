package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  public static final String SWITCH = "#switch";

  private static final Pattern SWITCH_PARAMETER_REGEX = Pattern.compile("([^=]+)=([^=]+)");

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
      // evaluate() wraps this in a try catch, which we don't want.
      parameters.get(0).call();
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

  public static String switchExpression(List<Callable<String>> parameters) {
    checkMinParameterCount(SWITCH, parameters, 2);
    String switchValue = evaluate(parameters.get(0));

    // Default is the last value, and if values aren't specified then you move forward to the next
    // key until you get a value.
    // Given this the only sensible way to go through the parameters is backwards.
    List<Callable<String>> casesToEvaluate =
        new ArrayList<>(parameters.subList(1, parameters.size()));
    Collections.reverse(casesToEvaluate);

    String lastValue = "";
    Optional<String> defaultValue = Optional.empty();
    for (Callable<String> p : casesToEvaluate) {
      String currentRule = evaluate(p);

      Matcher matcher = SWITCH_PARAMETER_REGEX.matcher(currentRule);
      if (matcher.matches()) {
        String compareAgainst = matcher.group(1);
        String valueIfTrue = matcher.group(2);
        lastValue = valueIfTrue;

        // Always set default if it is provided.
        if (compareAgainst.equals("#default")) {
          defaultValue = Optional.of(valueIfTrue);
          continue;
        }

        // So we're not setting default, meaning we are just comparing the keys.
        if (switchValue.equals(compareAgainst)) {
          return valueIfTrue;
        }

        // Default is the last parameter. The optional is always empty on the first iteration, and
        // full otherwise.
        if (defaultValue.isEmpty()) {
          defaultValue = Optional.of(valueIfTrue);
        }
      } else {
        // Default is the last parameter. The optional is always empty on the first iteration, and
        // full otherwise.
        if (defaultValue.isEmpty()) {
          defaultValue = Optional.of(currentRule);
        }

        // This is the case where we've matched a key without a specified value. That means the next
        // key with a specified value.
        // Since we are doing this in reverse, that means the last one.
        if (currentRule.equals(switchValue)) {
          return lastValue;
        }
      }
    }

    return defaultValue.orElse(lastValue);
  }
}
