grammar WikiText;
@header{ package com.lucaskjaerozhang.wikitext_parser; }
r   : 'hello' ID;
ID  : [a-z]+ ;
WS  : [ \t\r\n]+ -> skip ;