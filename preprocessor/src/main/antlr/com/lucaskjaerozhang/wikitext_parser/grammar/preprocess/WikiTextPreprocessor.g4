grammar WikiTextPreprocessor;

root
   : elements+
   ;

elements
   : nowikiBlock
   | unresolvedTemplateParameter
   | template
   | preprocessorDirective
   | any
   ;

nowikiBlock
   : OPEN_CARAT 'nowiki' CLOSE_CARAT any+ OPEN_CARAT 'nowiki' ' '? SLASH CLOSE_CARAT
   ;

unresolvedTemplateParameter
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionName PIPE? parserFunctionCharacters* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE
   ;

template
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithNoParameters
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ templateParameter+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithParameters
   ;

templateName
   : TEXT
   | DASH
   ;

templateParameter
   : PIPE templateParameterKeyValue # UnnamedParameter
   | PIPE templateParameterKeyValue EQUALS templateParameterParameterValue # NamedParameter
   ;

templateParameterKeyValue
   : templateParameterKeyValuesUnion+
   ;

templateParameterParameterValue
   : templateParameterParameterValuesUnion+
   ;

templateParameterKeyValuesUnion
   : TEXT
   | COLON
   | DASH
   | HASH
   | SLASH
   | UNDERSCORE
   | ANY
   ;

link
   : OPEN_SQUARE_BRACE OPEN_SQUARE_BRACE TEXT (PIPE TEXT)? CLOSE_SQUARE_BRACE CLOSE_SQUARE_BRACE
   ;

templateParameterParameterValuesUnion
   : templateParameterKeyValuesUnion
   | EQUALS
   | link
   ;

preprocessorDirective
   : behaviorSwitch
   | parserFunction
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE TEXT UNDERSCORE UNDERSCORE
   ;

parserFunction
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionName COLON parserFunctionParameter (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # RegularParserFunction
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionName COLON (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # ParserFunctionWithBlankFirstParameter
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
   : parserFunctionParameterValue # ParserFunctionTextParameter
   | parserFunction # ParserFunctionFunctionParameter
   ;

parserFunctionParameterValue
   : parserFunctionParameterValues+
   ;

parserFunctionParameterValues
   : TEXT
   | COLON
   | EQUALS
   | SLASH
   | unresolvedTemplateParameter
   | parserFunction
   ;

any
   : link
   | nonControlCharacters
   | OPEN_CURLY_BRACE
   | OPEN_CARAT
   | UNDERSCORE
   ;

nonControlCharacters
   : ~ (OPEN_CURLY_BRACE | OPEN_CARAT | UNDERSCORE)+
   ;

CLOSE_CURLY_BRACE
   : '}'
   ;

CLOSE_SQUARE_BRACE
   : ']'
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

OPEN_CURLY_BRACE
   : '{'
   ;

OPEN_SQUARE_BRACE
   : '['
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
   : [\p{Alnum} \n]+
   ;

UNDERSCORE
   : '_'
   ;

ANY
   : .+?
   ;

