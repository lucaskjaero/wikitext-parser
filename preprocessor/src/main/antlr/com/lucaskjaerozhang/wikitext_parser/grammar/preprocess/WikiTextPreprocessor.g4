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
   : OPEN_BRACE OPEN_BRACE OPEN_BRACE parserFunctionName PIPE? parserFunctionCharacters+ CLOSE_BRACE CLOSE_BRACE CLOSE_BRACE
   ;

template
   : OPEN_BRACE OPEN_BRACE templateName+ CLOSE_BRACE CLOSE_BRACE # TemplateWithNoParameters
   | OPEN_BRACE OPEN_BRACE templateName+ templateParameter+ CLOSE_BRACE CLOSE_BRACE # TemplateWithParameters
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
   | SLASH
   | HASH
   | UNDERSCORE
   | ANY
   ;

templateParameterParameterValuesUnion
   : templateParameterKeyValuesUnion
   | EQUALS
   ;

preprocessorDirective
   : behaviorSwitch
   | parserFunction
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE TEXT UNDERSCORE UNDERSCORE
   ;

parserFunction
   : OPEN_BRACE OPEN_BRACE parserFunctionName COLON parserFunctionParameter+ CLOSE_BRACE CLOSE_BRACE
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
   | EQUALS
   | SLASH
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
   : [\p{Alnum} ]+
   ;

UNDERSCORE
   : '_'
   ;

ANY
   : .+?
   ;

