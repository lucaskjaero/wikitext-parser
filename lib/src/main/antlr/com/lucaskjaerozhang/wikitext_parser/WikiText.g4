grammar WikiText;

@ header
{ package com.lucaskjaerozhang.wikitext_parser.grammar; }
root
   : value value*
   ;

value
   : header
   | TEXT
   | HORIZONTAL_RULE
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

HORIZONTAL_RULE
   : '----'
   ;

WS
   : [ \t\r\n]+ -> skip
   ;

