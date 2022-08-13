lexer grammar Tokens;

TEXT
   : [a-zA-Z0-9 ]+
   ;

SPACE
   : ' '
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

SEMICOLON
   : ';'
   ;

ASTERISK
   : '*'
   ;

HASH
   : '#'
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

OPEN_CARAT
   : '<'
   ;

CLOSE_CARAT
   : '>'
   ;

SLASH
   : '/'
   ;

SINGLE_QUOTE
   : '\''
   ;

DOUBLE_QUOTE
   : '"'
   ;

