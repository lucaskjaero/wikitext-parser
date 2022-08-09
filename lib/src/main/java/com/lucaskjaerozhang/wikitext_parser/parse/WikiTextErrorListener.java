package com.lucaskjaerozhang.wikitext_parser.parse;

import java.util.BitSet;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

public class WikiTextErrorListener implements ANTLRErrorListener {
  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e) {
    throw e;
  }

  @Override
  public void reportAmbiguity(
      Parser recognizer,
      DFA dfa,
      int startIndex,
      int stopIndex,
      boolean exact,
      BitSet ambigAlts,
      ATNConfigSet configs) {}

  @Override
  public void reportAttemptingFullContext(
      Parser recognizer,
      DFA dfa,
      int startIndex,
      int stopIndex,
      BitSet conflictingAlts,
      ATNConfigSet configs) {}

  @Override
  public void reportContextSensitivity(
      Parser recognizer,
      DFA dfa,
      int startIndex,
      int stopIndex,
      int prediction,
      ATNConfigSet configs) {}
}
