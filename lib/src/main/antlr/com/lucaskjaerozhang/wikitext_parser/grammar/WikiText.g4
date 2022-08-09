grammar WikiText;

root
   : sectionStart
   | sectionContent+
   ;

sectionStart
   : sectionLevelOne+ # SectionsLevelOne
   | sectionLevelTwo+ # SectionsLevelTwo
   | sectionLevelThree+ # SectionsLevelThree
   | sectionLevelFour+ # SectionsLevelFour
   | sectionLevelFive+ # SectionsLevelFive
   | sectionLevelSix+ # SectionsLevelSix
   ;

sectionLevelOne
   : ONE_EQUAL singleLineValue ONE_EQUAL sectionOneContent+
   ;

sectionOneContent
   : sectionContent+
   | sectionLevelTwo+
   ;

sectionLevelTwo
   : TWO_EQUALS singleLineValue TWO_EQUALS sectionTwoContent+
   ;

sectionTwoContent
   : sectionContent+
   | sectionLevelThree+
   ;

sectionLevelThree
   : THREE_EQUALS singleLineValue THREE_EQUALS sectionThreeContent+
   ;

sectionThreeContent
   : sectionContent+
   | sectionLevelFour+
   ;

sectionLevelFour
   : FOUR_EQUALS singleLineValue FOUR_EQUALS sectionFourContent+
   ;

sectionFourContent
   : sectionContent+
   | sectionLevelFive+
   ;

sectionLevelFive
   : FIVE_EQUALS singleLineValue FIVE_EQUALS sectionFiveContent+
   ;

sectionFiveContent
   : sectionContent+
   | sectionLevelSix+
   ;

sectionLevelSix
   : SIX_EQUALS singleLineValue SIX_EQUALS sectionContent+
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

