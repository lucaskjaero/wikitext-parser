package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class DateAndTimeFunctionEvaluator extends BaseFunctionEvaluator {
  public static final String CURRENTMONTH = "CURRENTMONTH";
  public static final String TIME = "#time";

  public static String currentMonth() {
    return String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
  }

  public static Optional<String> time(List<Callable<String>> parameters) {
    checkParameterCount(TIME, parameters, 1, 2);

    return Optional.of(
        String.format(
            "<module name='time'>%s</module>",
            evaluate(parameters).stream()
                .map(parameter -> String.format("<argument>%s</argument>", parameter))
                .collect(Collectors.joining())));
  }
}
