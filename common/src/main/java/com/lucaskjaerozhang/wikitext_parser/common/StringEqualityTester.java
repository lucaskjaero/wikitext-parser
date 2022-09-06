package com.lucaskjaerozhang.wikitext_parser.common;

public class StringEqualityTester {
  public static boolean equalsIgnoreCaseExceptFirstLetter(String first, String second) {
    return toLowerExceptFirstLetter(first).equals(toLowerExceptFirstLetter(second));
  }

  public static String toLowerExceptFirstLetter(String input) {
    if (input.length() <= 1) return input;
    return input.substring(0, 1).concat(input.substring(1).toLowerCase());
  }
}
