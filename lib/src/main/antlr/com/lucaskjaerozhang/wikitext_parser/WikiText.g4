grammar WikiText;

@ header
{ package com.lucaskjaerozhang.wikitext_parser.grammar; }
root
   : value value*
   ;

value
   : header
   | TEXT
   ;

header
   : '=' value '=' # HeaderLevelOne
   | '==' value '==' # HeaderLevelTwo
   | '===' value '===' # HeaderLevelThree
   | '====' value '====' # HeaderLevelFour
   | '=====' value '=====' # HeaderLevelFive
   | '======' value '======' # HeaderLevelSix
   ;

TEXT
   : [a-zA-Z0-9]+
   ;

WS
   : [ \t\r\n]+ -> skip
   ;

