grammar WikiText;

import Tokens;
root
   : redirect
   | baseElements+
   ;

redirect
   : '#REDIRECT ' wikiLink
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
   | mathBlock
   | noWikiBlock
   | indentedBlock
   | bold
   | italics
   | xmlTag
   | unorderedList
   | orderedList
   | descriptionList
   | wikiLink
   | externalLink
   | horizontalRule
   | LINE_BREAK
   | NEWLINE
   | text
   ;

codeBlock
   : OPEN_CARAT SPACE* 'code' tagAttribute* CLOSE_CARAT anySequence OPEN_CARAT SLASH SPACE* 'code' SPACE* CLOSE_CARAT
   ;

syntaxHighlightBlock
   : OPEN_CARAT SPACE* 'syntaxhighlight' tagAttribute* CLOSE_CARAT anySequence OPEN_CARAT SLASH SPACE* 'syntaxhighlight' SPACE* CLOSE_CARAT
   ;

mathBlock
   : OPEN_CARAT SPACE* 'math' tagAttribute* CLOSE_CARAT anySequence OPEN_CARAT SLASH SPACE* 'math' SPACE* CLOSE_CARAT
   ;

noWikiBlock
   : OPEN_CARAT SPACE* 'nowiki' tagAttribute* CLOSE_CARAT anySequence OPEN_CARAT SLASH SPACE* 'nowiki' SPACE* CLOSE_CARAT
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
   : OPEN_CARAT textWithoutSpaces tagAttribute* SPACE* CLOSE_CARAT sectionContent+ OPEN_CARAT SLASH textWithoutSpaces CLOSE_CARAT # ContainerXMLTag
   | OPEN_CARAT textWithoutSpaces tagAttribute* SPACE* SLASH CLOSE_CARAT # StandaloneXMLTag
   ;

tagAttribute
   : SPACE* tagAttributeKeyValues EQUALS SINGLE_QUOTE tagAttributeValues+ SINGLE_QUOTE # SingleQuoteTagAttribute
   | SPACE* tagAttributeKeyValues EQUALS DOUBLE_QUOTE tagAttributeValues+ DOUBLE_QUOTE # DoubleQuoteTagAttribute
   ;

tagAttributeKeyValues
   : textWithoutSpaces
   | COLON
   | SEMICOLON
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

wikiLink
   : OPEN_BRACKET OPEN_BRACKET COLON wikiLinkTarget CLOSE_BRACKET CLOSE_BRACKET # VisibleCategoryLink
   | OPEN_BRACKET OPEN_BRACKET COLON wikiLinkTarget PIPE CLOSE_BRACKET CLOSE_BRACKET # AutomaticallyRenamedCategoryLink
   | OPEN_BRACKET OPEN_BRACKET wikiLinkTarget CLOSE_BRACKET CLOSE_BRACKET # BaseWikiLink
   | OPEN_BRACKET OPEN_BRACKET wikiLinkTarget PIPE text+ CLOSE_BRACKET CLOSE_BRACKET # RenamedWikiLink
   | OPEN_BRACKET OPEN_BRACKET wikiLinkTarget PIPE CLOSE_BRACKET CLOSE_BRACKET # AutomaticallyRenamedWikiLink
   ;

wikiLinkTarget
   : wikiLinkNamespaceComponent* text+ wikiLinkSectionComponent?
   ;

wikiLinkNamespaceComponent
   : text COLON
   ;

wikiLinkSectionComponent
   : HASH text+
   ;

externalLink
   : OPEN_BRACKET urlCharacters+ CLOSE_BRACKET # UnnamedExternalLink
   | OPEN_BRACKET urlCharacters+ SPACE text+ CLOSE_BRACKET # NamedExternalLink
   ;

urlCharacters
   : TEXT
   | COLON
   | SLASH
   | PERIOD
   | QUESTION_MARK
   | HASH
   | AMPERSAND
   | EQUALS
   | PERCENT_SIGN
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
   | CHARACTER_REFERENCE
   ;

textWithoutSpaces
   : textUnionNoSpaces+
   ;

textUnionNoSpaces
   : TEXT
   | DASH
   | CHARACTER_REFERENCE
   ;

anySequence
   : .+?
   ;

