grammar WikiText;

root
   : baseElements+
   ;

baseElements
   : sectionLevelOne
   | sectionLevelTwo
   | sectionLevelThree
   | sectionLevelFour
   | sectionLevelFive
   | sectionLevelSix
   | sectionContent
   ;

sectionLevelOne
   : ONE_EQUAL ' '* TEXT ' '* ONE_EQUAL sectionOneContent+
   ;

sectionOneContent
   : sectionContent
   | sectionLevelTwo
   ;

sectionLevelTwo
   : TWO_EQUALS ' '* TEXT ' '* TWO_EQUALS sectionTwoContent+
   ;

sectionTwoContent
   : sectionContent
   | sectionLevelThree
   ;

sectionLevelThree
   : THREE_EQUALS ' '* TEXT ' '* THREE_EQUALS sectionThreeContent+
   ;

sectionThreeContent
   : sectionContent
   | sectionLevelFour
   ;

sectionLevelFour
   : FOUR_EQUALS ' '* TEXT ' '* FOUR_EQUALS sectionFourContent+
   ;

sectionFourContent
   : sectionContent
   | sectionLevelFive
   ;

sectionLevelFive
   : FIVE_EQUALS ' '* TEXT ' '* FIVE_EQUALS sectionFiveContent+
   ;

sectionFiveContent
   : sectionContent
   | sectionLevelSix
   ;

sectionLevelSix
   : SIX_EQUALS ' '* TEXT ' '* SIX_EQUALS sectionContent+
   ;

sectionContent
   : indentedBlock
   | blockQuote
   | HORIZONTAL_RULE
   | LINE_BREAK
   | NEWLINE
   | TEXT
   ;

indentedBlock
   : COLON indentedBlock
   | COLON TEXT+ NEWLINE
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

