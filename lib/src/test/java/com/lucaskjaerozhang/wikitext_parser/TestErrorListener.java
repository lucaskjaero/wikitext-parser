package com.lucaskjaerozhang.wikitext_parser;

import java.util.BitSet;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.junit.jupiter.api.Assertions;

/** We mostly want errors from DiagnosticErrorListener, but want to customize it a bit.. */
public class TestErrorListener /*extends DiagnosticErrorListener*/ implements ANTLRErrorListener {

  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e) {
    Assertions.fail(msg, e);
  }

  @Override
  public void reportAmbiguity(
      org.antlr.v4.runtime.Parser recognizer,
      DFA dfa,
      int startIndex,
      int stopIndex,
      boolean exact,
      BitSet ambigAlts,
      ATNConfigSet configs) {
    /* One error at a time. */
  }

  @Override
  public void reportContextSensitivity(
      org.antlr.v4.runtime.Parser recognizer,
      DFA dfa,
      int startIndex,
      int stopIndex,
      int prediction,
      ATNConfigSet configs) {
    /* One error at a time. */
  }

  @Override
  public void reportAttemptingFullContext(
      Parser recognizer,
      DFA dfa,
      int startIndex,
      int stopIndex,
      BitSet conflictingAlts,
      ATNConfigSet configs) {
    /*
    Intentionally suppressing this for now as it indicates inefficiency, not actual ambiguity.
    I will optimize the grammar when it's fully written, but for now it's premature.
    */
  }
}
