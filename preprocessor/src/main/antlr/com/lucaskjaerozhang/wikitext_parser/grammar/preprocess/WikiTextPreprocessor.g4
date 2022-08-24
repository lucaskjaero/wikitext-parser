grammar WikiTextPreprocessor;

root
   : elements+
   ;

elements
   : nowikiBlock
   | preprocessorDirective
   | anySequence
   ;

nowikiBlock
   : OPEN_CARAT 'nowiki' CLOSE_CARAT anySequence OPEN_CARAT SLASH 'nowiki' CLOSE_CARAT
   ;

preprocessorDirective
   : behaviorSwitch
   | parserFunction
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE CAPITAL_LETTERS UNDERSCORE UNDERSCORE
   ;

parserFunction
   : OPEN_BRACE OPEN_BRACE parserFunctionCharacters+ CLOSE_BRACE CLOSE_BRACE # Variable
   | OPEN_BRACE OPEN_BRACE parserFunctionCharacters+ COLON parserFunctionParameter+ CLOSE_BRACE CLOSE_BRACE # ParserFunctionWithParameters
   ;

parserFunctionCharacters
   : TEXT
   | HASH
   | EXCLAMATION_MARK
   | EQUALS
   ;

parserFunctionParameter
   : PIPE? TEXT
   | PIPE? parserFunction
   ;

anySequence
   : .+?
   ;

CAPITAL_LETTERS
   : [A-Z]+
   ;

CLOSE_BRACE
   : '}'
   ;

CLOSE_CARAT
   : '>'
   ;

COLON
   : ':'
   ;

EQUALS
   : '='
   ;

EXCLAMATION_MARK
   : '!'
   ;

HASH
   : '#'
   ;

OPEN_BRACE
   : '{'
   ;

OPEN_CARAT
   : '<'
   ;

PIPE
   : '|'
   ;

SLASH
   : '/'
   ;

TEXT
   : [\p{Alnum}]+
   ;

UNDERSCORE
   : '_'
   ;

