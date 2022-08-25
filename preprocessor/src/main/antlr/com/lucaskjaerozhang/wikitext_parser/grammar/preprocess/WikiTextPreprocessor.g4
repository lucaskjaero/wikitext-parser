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
   : OPEN_CARAT 'nowiki' CLOSE_CARAT anySequence OPEN_CARAT 'nowiki' ' '? SLASH CLOSE_CARAT
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
   : PIPE? parserFunctionParameterValue # ParserFunctionTextParameter
   | PIPE? parserFunction # ParserFunctionFunctionParameter
   ;

parserFunctionParameterValue
   : parserFunctionParameterValues+
   ;

parserFunctionParameterValues
   : TEXT
   | COLON
   ;

anySequence
   : any+
   ;

any
   : nonControlCharacters
   | OPEN_BRACE
   | OPEN_CARAT
   | UNDERSCORE
   ;

nonControlCharacters
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

