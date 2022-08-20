lexer grammar Tokens;
// Explicitly enumerating punctuation so we don't hit control characters

TEXT
   : [\p{Alnum},.?，。《》？()]+
   ;

SPACE
   : ' '
   ;

DASH
   : '-'
   ;

PERIOD
   : '.'
   ;

QUESTION_MARK
   : '?'
   ;

AMPERSAND
   : '&'
   ;

PERCENT_SIGN
   : '%'
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

OPEN_BRACE
   : '{'
   ;

CLOSE_BRACE
   : '}'
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

PIPE
   : '|'
   ;

SINGLE_QUOTE
   : '\''
   ;

DOUBLE_QUOTE
   : '"'
   ;

OPEN_BRACKET
   : '['
   ;

CLOSE_BRACKET
   : ']'
   ;

CHARACTER_REFERENCE
   : '&' [A-Za-z0-9#]+ SEMICOLON
   ;

ANY
   : .+?
   ;

