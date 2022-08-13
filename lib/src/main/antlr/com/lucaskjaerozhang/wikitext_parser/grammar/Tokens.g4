lexer grammar Tokens;

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

