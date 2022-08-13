grammar WikiText;

import Tokens;
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
   : ONE_EQUAL TEXT ONE_EQUAL sectionOneContent+
   ;

sectionOneContent
   : sectionContent
   | sectionLevelTwo
   ;

sectionLevelTwo
   : TWO_EQUALS TEXT TWO_EQUALS sectionTwoContent+
   ;

sectionTwoContent
   : sectionContent
   | sectionLevelThree
   ;

sectionLevelThree
   : THREE_EQUALS TEXT THREE_EQUALS sectionThreeContent+
   ;

sectionThreeContent
   : sectionContent
   | sectionLevelFour
   ;

sectionLevelFour
   : FOUR_EQUALS TEXT FOUR_EQUALS sectionFourContent+
   ;

sectionFourContent
   : sectionContent
   | sectionLevelFive
   ;

sectionLevelFive
   : FIVE_EQUALS TEXT FIVE_EQUALS sectionFiveContent+
   ;

sectionFiveContent
   : sectionContent
   | sectionLevelSix
   ;

sectionLevelSix
   : SIX_EQUALS TEXT SIX_EQUALS sectionContent+
   ;

sectionContent
   : indentedBlock
   | blockQuote
   | unorderedList
   | orderedList
   | descriptionList
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

unorderedList
   : unorderedListItem+
   ;

unorderedListItem
   : ASTERISK TEXT+ NEWLINE
   | ASTERISK unorderedListItem
   ;

orderedList
   : orderedListItem+
   ;

orderedListItem
   : HASH TEXT+ NEWLINE
   | HASH orderedListItem
   ;

descriptionList
   : SEMICOLON TEXT+ NEWLINE? descriptionListItem+
   ;

descriptionListItem
   : COLON TEXT+ NEWLINE?
   ;

