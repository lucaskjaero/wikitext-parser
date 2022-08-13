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

CODE
   : C O D E
   ;

SYNTAX_HIGHLIGHT
   : S Y N T A X H I G H L I G H T
   ;

fragment A
   : [aA]
   ;

fragment B
   : [bB]
   ;

fragment C
   : [cC]
   ;

fragment D
   : [dD]
   ;

fragment E
   : [eE]
   ;

fragment F
   : [fF]
   ;

fragment G
   : [gG]
   ;

fragment H
   : [hH]
   ;

fragment I
   : [iI]
   ;

fragment J
   : [jJ]
   ;

fragment K
   : [kK]
   ;

fragment L
   : [lL]
   ;

fragment M
   : [mM]
   ;

fragment N
   : [nN]
   ;

fragment O
   : [oO]
   ;

fragment P
   : [pP]
   ;

fragment Q
   : [qQ]
   ;

fragment R
   : [rR]
   ;

fragment S
   : [sS]
   ;

fragment T
   : [tT]
   ;

fragment U
   : [uU]
   ;

fragment V
   : [vV]
   ;

fragment W
   : [wW]
   ;

fragment X
   : [xX]
   ;

fragment Y
   : [yY]
   ;

fragment Z
   : [zZ]
   ;

ANY
   : .+?
   ;

