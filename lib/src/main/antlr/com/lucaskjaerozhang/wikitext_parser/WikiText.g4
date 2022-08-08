grammar WikiText;

@ header
{ package com.lucaskjaerozhang.wikitext_parser.grammar; }
root
   : value+
   ;

value
   : header
   | indentedBlock
   | blockQuote
   | HORIZONTAL_RULE
   | LINE_BREAK
   | NEWLINE
   | singleLineValue+
   ;
/*
singleLineValue is all values that can be put on one line without \n
We use it when a pattern will be terminated by newlines
*/
   
   
singleLineValue
   : TEXT
   ;

header
   : ONE_EQUAL singleLineValue ONE_EQUAL # HeaderLevelOne
   | TWO_EQUALS singleLineValue TWO_EQUALS # HeaderLevelTwo
   | THREE_EQUALS singleLineValue THREE_EQUALS # HeaderLevelThree
   | FOUR_EQUALS singleLineValue FOUR_EQUALS # HeaderLevelFour
   | FIVE_EQUALS singleLineValue FIVE_EQUALS # HeaderLevelFive
   | SIX_EQUALS singleLineValue SIX_EQUALS # HeaderLevelSix
   ;

indentedBlock
   : COLON indentedBlock
   | COLON singleLineValue NEWLINE
   ;

blockQuote
   : BLOCKQUOTE_OPEN value+ BLOCKQUOTE_CLOSE
   ;

TEXT
   : [a-zA-Z0-9 ]+
   ;

HORIZONTAL_RULE
   : '----'
   ;

LINE_BREAK
   : NEWLINE NEWLINE
   ;

NEWLINE
   : '\n'
   ;

COLON
   : ':'
   ;

ONE_EQUAL
   : '='
   ;

TWO_EQUALS
   : '=='
   ;

THREE_EQUALS
   : '==='
   ;

FOUR_EQUALS
   : '===='
   ;

FIVE_EQUALS
   : '====='
   ;

SIX_EQUALS
   : '======'
   ;

WS
   : [ \t\r]+ -> skip
   ;
/* OUTDENT is a visual indicator that indentation has finished but has no actual impact.*/
   
   
OUTDENT
   : '{{Outdent|' ':'+ '}}' -> skip
   ;

BLOCKQUOTE_OPEN
   : '<blockquote>'
   ;

BLOCKQUOTE_CLOSE
   : '</blockquote>'
   ;

