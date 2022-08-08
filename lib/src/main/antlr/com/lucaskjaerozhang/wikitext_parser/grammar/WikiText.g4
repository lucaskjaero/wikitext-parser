grammar WikiText;

root
   : sectionStart
   | sectionContent+
   ;

sectionStart
   : sectionLevelOne+
   | sectionLevelTwo+
   | sectionLevelThree+
   | sectionLevelFour+
   | sectionLevelFive+
   | sectionLevelSix+
   ;

sectionLevelOne
   : headerLevelOne sectionOneContent+ sectionLevelOne*
   ;

sectionOneContent
   : sectionContent+
   | sectionLevelTwo+
   ;

sectionLevelTwo
   : headerLevelTwo sectionTwoContent+ sectionLevelTwo*
   ;

sectionTwoContent
   : sectionContent+
   | sectionLevelThree+
   ;

sectionLevelThree
   : headerLevelThree sectionThreeContent+ sectionLevelThree*
   ;

sectionThreeContent
   : sectionContent+
   | sectionLevelFour+
   ;

sectionLevelFour
   : headerLevelFour sectionFourContent+ sectionLevelFour*
   ;

sectionFourContent
   : sectionContent+
   | sectionLevelFive+
   ;

sectionLevelFive
   : headerLevelFive sectionFiveContent+ sectionLevelFive*
   ;

sectionFiveContent
   : sectionContent+
   | sectionLevelSix+
   ;

sectionLevelSix
   : headerLevelSix sectionContent+ sectionLevelSix*
   ;

headerLevelOne
   : ONE_EQUAL singleLineValue ONE_EQUAL
   ;

headerLevelTwo
   : TWO_EQUALS singleLineValue TWO_EQUALS
   ;

headerLevelThree
   : THREE_EQUALS singleLineValue THREE_EQUALS
   ;

headerLevelFour
   : FOUR_EQUALS singleLineValue FOUR_EQUALS
   ;

headerLevelFive
   : FIVE_EQUALS singleLineValue FIVE_EQUALS
   ;

headerLevelSix
   : SIX_EQUALS singleLineValue SIX_EQUALS
   ;

sectionContent
   : indentedBlock
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

indentedBlock
   : COLON indentedBlock
   | COLON singleLineValue NEWLINE
   ;

blockQuote
   : BLOCKQUOTE_OPEN sectionContent+ BLOCKQUOTE_CLOSE
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

