package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.Calendar;

public class DateAndTimeFunctionEvaluator extends BaseFunctionEvaluator {
  public static final String CURRENTMONTH = "CURRENTMONTH";

  public static String currentMonth() {
    return String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
  }
}
