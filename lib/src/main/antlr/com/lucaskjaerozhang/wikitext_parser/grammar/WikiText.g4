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
   : EQUALS text EQUALS sectionOneContent+
   ;

sectionOneContent
   : sectionContent
   | sectionLevelTwo
   | sectionLevelThree
   | sectionLevelFour
   | sectionLevelFive
   | sectionLevelSix
   ;

sectionLevelTwo
   : EQUALS EQUALS text EQUALS EQUALS sectionTwoContent+
   ;

sectionTwoContent
   : sectionContent
   | sectionLevelThree
   | sectionLevelFour
   | sectionLevelFive
   | sectionLevelSix
   ;

sectionLevelThree
   : EQUALS EQUALS EQUALS text EQUALS EQUALS EQUALS sectionThreeContent+
   ;

sectionThreeContent
   : sectionContent
   | sectionLevelFour
   | sectionLevelFive
   | sectionLevelSix
   ;

sectionLevelFour
   : EQUALS EQUALS EQUALS EQUALS text EQUALS EQUALS EQUALS EQUALS sectionFourContent+
   ;

sectionFourContent
   : sectionContent
   | sectionLevelFive
   | sectionLevelSix
   ;

sectionLevelFive
   : EQUALS EQUALS EQUALS EQUALS EQUALS text EQUALS EQUALS EQUALS EQUALS EQUALS sectionFiveContent+
   ;

sectionFiveContent
   : sectionContent
   | sectionLevelSix
   ;

sectionLevelSix
   : EQUALS EQUALS EQUALS EQUALS EQUALS EQUALS text EQUALS EQUALS EQUALS EQUALS EQUALS EQUALS sectionContent+
   ;

sectionContent
   : codeBlock
   | syntaxHighlightBlock
   | indentedBlock
   | bold
   | italics
   | xmlTag
   | unorderedList
   | orderedList
   | descriptionList
   | horizontalRule
   | LINE_BREAK
   | NEWLINE
   | text
   ;

codeBlock
   : OPEN_CARAT SPACE* CODE tagAttribute* CLOSE_CARAT ANY OPEN_CARAT SLASH SPACE* CODE SPACE* CLOSE_CARAT
   ;

syntaxHighlightBlock
   : OPEN_CARAT SPACE* SYNTAX_HIGHLIGHT tagAttribute* CLOSE_CARAT ANY OPEN_CARAT SLASH SPACE* SYNTAX_HIGHLIGHT SPACE* CLOSE_CARAT
   ;

indentedBlock
   : COLON indentedBlock
   | COLON text NEWLINE
   ;

bold
   : SINGLE_QUOTE SINGLE_QUOTE SINGLE_QUOTE text SINGLE_QUOTE SINGLE_QUOTE SINGLE_QUOTE # BoldText
   | SINGLE_QUOTE SINGLE_QUOTE SINGLE_QUOTE italics SINGLE_QUOTE SINGLE_QUOTE SINGLE_QUOTE # BoldItalicText
   ;

italics
   : SINGLE_QUOTE SINGLE_QUOTE text SINGLE_QUOTE SINGLE_QUOTE
   ;

xmlTag
   : OPEN_CARAT text tagAttribute* CLOSE_CARAT sectionContent+ OPEN_CARAT SLASH text CLOSE_CARAT
   ;

tagAttribute
   : SPACE* text EQUALS SINGLE_QUOTE tagAttributeValues+ SINGLE_QUOTE SPACE*
   | SPACE* text EQUALS DOUBLE_QUOTE tagAttributeValues+ DOUBLE_QUOTE SPACE*
   ;

tagAttributeValues
   : text
   | COLON
   | SEMICOLON
   ;

unorderedList
   : unorderedListItem+
   ;

unorderedListItem
   : ASTERISK text NEWLINE # TerminalUnorderedListItem
   | ASTERISK unorderedListItem # EnclosingUnorderedListItem
   ;

orderedList
   : orderedListItem+
   ;

orderedListItem
   : HASH text NEWLINE # TerminalOrderedListItem
   | HASH orderedListItem # EnclosingOrderedListItem
   ;

descriptionList
   : SEMICOLON text NEWLINE? descriptionListItem+
   ;

descriptionListItem
   : COLON text NEWLINE?
   ;

horizontalRule
   : DASH DASH DASH DASH
   ;

text
   : textUnion+
   ;
   // Addresses punctuation that is also in markup
   
textUnion
   : TEXT
   | SPACE
   | DASH
   ;

