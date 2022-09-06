package com.lucaskjaerozhang.wikitext_parser.common;

public class StringEqualityTester {
  public static boolean equalsIgnoreCaseExceptFirstLetter(String first, String second) {
    if (first.length() <= 1 || second.length() <= 1) {
      return first.equals(second);
    }
    String firstNormalized = first.substring(0, 1).concat(first.substring(1).toLowerCase());
    String secondNormalized = second.substring(0, 1).concat(second.substring(1).toLowerCase());
    return firstNormalized.equals(secondNormalized);
  }
}
