package com.lucaskjaerozhang.wikitext_parser.preprocess.function;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseFunctionEvaluatorTest {
  @Test
  void testCheckParameterCount() {
    List<String> parametersOne = List.of("parameter1");
    List<String> parametersTwo = List.of("parameter1", "parameter2");
    List<String> parametersThree = List.of("parameter1", "parameter2", "parameter3");

    Assertions.assertDoesNotThrow(
        () -> BaseFunctionEvaluator.checkParameterCount("function", parametersTwo, 2));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> BaseFunctionEvaluator.checkParameterCount("function", parametersTwo, 1));

    Assertions.assertDoesNotThrow(
        () -> BaseFunctionEvaluator.checkParameterCount("function", parametersOne, 1, 3));
    Assertions.assertDoesNotThrow(
        () -> BaseFunctionEvaluator.checkParameterCount("function", parametersTwo, 1, 3));
    Assertions.assertDoesNotThrow(
        () -> BaseFunctionEvaluator.checkParameterCount("function", parametersThree, 1, 3));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> BaseFunctionEvaluator.checkParameterCount("function", parametersOne, 2, 3));
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> BaseFunctionEvaluator.checkParameterCount("function", parametersThree, 1, 2));

    Assertions.assertDoesNotThrow(
        () -> BaseFunctionEvaluator.checkMinParameterCount("function", parametersTwo, 1));
    Assertions.assertDoesNotThrow(
        () -> BaseFunctionEvaluator.checkMinParameterCount("function", parametersTwo, 2));
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> BaseFunctionEvaluator.checkMinParameterCount("function", parametersOne, 2));
  }
}
