grammar WikiTextPreprocessor;

root
   : elements+
   ;

elements
   : nowikiBlock
   | preprocessorDirective
   | anySequence
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
   : UNDERSCORE UNDERSCORE TEXT UNDERSCORE UNDERSCORE
   ;

parserFunction
   : OPEN_BRACE OPEN_BRACE parserFunctionName CLOSE_BRACE CLOSE_BRACE # Variable
   | OPEN_BRACE OPEN_BRACE parserFunctionName COLON parserFunctionParameter+ CLOSE_BRACE CLOSE_BRACE # ParserFunctionWithParameters
   ;

parserFunctionName
   : parserFunctionCharacters+
   ;

parserFunctionCharacters
   : ANY
   | DASH
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

