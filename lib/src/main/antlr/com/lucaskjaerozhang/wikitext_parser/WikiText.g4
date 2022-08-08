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
   : '=' singleLineValue '=' # HeaderLevelOne
   | '==' singleLineValue '==' # HeaderLevelTwo
   | '===' singleLineValue '===' # HeaderLevelThree
   | '====' singleLineValue '====' # HeaderLevelFour
   | '=====' singleLineValue '=====' # HeaderLevelFive
   | '======' singleLineValue '======' # HeaderLevelSix
   ;

indentedBlock
   : ':' indentedBlock
   | ':' singleLineValue NEWLINE
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

