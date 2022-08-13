lexer grammar Tokens;
// Explicitly enumerating punctuation so we don't hit control characters

TEXT
   : [\p{Alnum},.?]+
   ;

SPACE
   : ' '
   ;

DASH
   : '-'
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

EQUALS
   : '='
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

