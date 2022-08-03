grammar WikiText;
@header{ package com.lucaskjaerozhang.wikitext_parser; }
root
: header
| TEXT
;

header
: '=' TEXT '=' # HeaderLevelOne
| '==' TEXT '==' # HeaderLevelTwo
| '===' TEXT '===' # HeaderLevelThree
| '====' TEXT '====' # HeaderLevelFour
| '=====' TEXT '=====' # HeaderLevelFive
| '======' TEXT '======' # HeaderLevelSix
;

TEXT  : [a-zA-Z0-9]+ ;
WS  : [ \t\r\n]+ -> skip ;