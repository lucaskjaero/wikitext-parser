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
   : PIPE templateParameterKeyValues+ # UnnamedParameter
   | PIPE templateParameterKeyValues+ EQUALS templateParameterParameterValues+ # NamedParameter
   ;

templateParameterKeyValues
   : link
   | template
   | parserFunction
   | ' '
   | '"'
   | '\''
   | TEXT
   | COLON
   | DASH
   | HASH
   | SLASH
   | UNDERSCORE
   | ANY
   ;

templateParameterParameterValues
   : link
   | template
   | parserFunction
   | ' '
   | '"'
   | '\''
   | EQUALS
   | TEXT
   | COLON
   | DASH
   | HASH
   | SLASH
   | UNDERSCORE
   | ANY
   ;

link
   : '[' '[' linkNamespaceComponent* TEXT (PIPE elements+)? ']' ']'
   ;

linkNamespaceComponent
   : TEXT COLON
   ;

preprocessorDirective
   : behaviorSwitch
   | parserFunction
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE TEXT UNDERSCORE UNDERSCORE
   ;

parserFunction
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionName COLON ('safesubst' COLON)? parserFunctionParameter (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # RegularParserFunction
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionName COLON ('safesubst' COLON)? (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # ParserFunctionWithBlankFirstParameter
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
   : parserFunctionParameterValues*
   ;

parserFunctionParameterValues
   : link
   | TEXT
   | DASH
   | COLON
   | EQUALS
   | SLASH
   | OPEN_CARAT
   | CLOSE_CARAT
   | ' '
   | '"'
   | '\''
   | ';'
   | unresolvedTemplateParameter
   | parserFunction
   | template
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

