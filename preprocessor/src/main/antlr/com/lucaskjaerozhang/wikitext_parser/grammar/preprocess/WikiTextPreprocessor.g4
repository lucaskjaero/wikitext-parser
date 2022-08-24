grammar WikiTextPreprocessor;

root
   : elements+
   ;

elements
   : nowikiBlock
   | preprocessorDirective
   //   | anySequence
   | OPEN_BRACE
   | OPEN_CARAT
   | UNDERSCORE
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
   : DASH
   | EXCLAMATION_MARK
   | EQUALS
   | HASH
   | TEXT
   ;

parserFunctionParameter
   : PIPE? TEXT # ParserFunctionTextParameter
   | PIPE? parserFunction # ParserFunctionFunctionParameter
   ;

anySequence
   : ~ (OPEN_BRACE | OPEN_CARAT | UNDERSCORE)+
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

DASH
   : '-'
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

ANY
   : .+?
   ;

