grammar WikiTextPreprocessor;

root
   : elements+
   ;

elements
   : nowikiBlock
   | templateParameterPlaceholder
   | template
   | preprocessorDirective
   | any
   ;

nowikiBlock
   : OPEN_CARAT 'nowiki' CLOSE_CARAT any+ OPEN_CARAT 'nowiki' ' '? SLASH CLOSE_CARAT
   ;

templateParameterPlaceholder
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionName PIPE parserFunctionCharacters* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateParameterPlaceholderWithDefault
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionName CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateParameterPlaceholderWithoutDefault
   ;

template
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithNoParameters
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ templateParameter+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithParameters
   ;

templateName
   : TEXT
   | DASH
   | SLASH
   ;

templateParameter
   : PIPE templateParameterKeyValues+ # UnnamedParameter
   | PIPE templateParameterKeyValues+ EQUALS templateParameterParameterValues+ # NamedParameter
   ;

templateParameterKeyValues
   : link
   | template
   | parserFunction
   | SPACE
   | DOUBLE_QUOTE
   | SINGLE_QUOTE
   | TEXT
   | COLON
   | DASH
   | HASH
   | PERIOD
   | SLASH
   | UNDERSCORE
   | ANY
   ;

templateParameterParameterValues
   : link
   | template
   | parserFunction
   | templateParameterPlaceholder
   | SPACE
   | DOUBLE_QUOTE
   | SINGLE_QUOTE
   | EQUALS
   | TEXT
   | COLON
   | DASH
   | HASH
   | PERIOD
   | SLASH
   | UNDERSCORE
   | ANY
   ;

link
   : OPEN_SQUARE_BRACE OPEN_SQUARE_BRACE linkNamespaceComponent* linkTarget+ (PIPE elements+)? CLOSE_SQUARE_BRACE CLOSE_SQUARE_BRACE
   ;

linkNamespaceComponent
   : TEXT COLON
   ;

linkTarget
   : TEXT
   | DASH
   ;

preprocessorDirective
   : behaviorSwitch
   | parserFunction
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE TEXT UNDERSCORE UNDERSCORE
   ;

parserFunction
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE ('safesubst' COLON)? parserFunctionName COLON ('safesubst' COLON)? parserFunctionParameter (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # RegularParserFunction
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE ('safesubst' COLON)? parserFunctionName COLON ('safesubst' COLON)? (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # ParserFunctionWithBlankFirstParameter
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
   | HASH
   | COLON
   | EQUALS
   | SLASH
   | OPEN_CARAT
   | CLOSE_CARAT
   | SPACE
   | DOUBLE_QUOTE
   | SINGLE_QUOTE
   | SEMICOLON
   | PERIOD
   | COMMA
   | CARAT
   | OPEN_SQUARE_BRACE
   | CLOSE_SQUARE_BRACE
   | PERCENT
   | STAR
   | UNDERSCORE
   | templateParameterPlaceholder
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

CARAT
   : '^'
   ;

COMMENT
   : '<!--' .*? '-->' -> skip
   ;

CLOSE_CURLY_BRACE
   : '}'
   ;

CLOSE_CARAT
   : '>'
   ;

CLOSE_SQUARE_BRACE
   : ']'
   ;

COLON
   : ':'
   ;

COMMA
   : ','
   ;

DASH
   : '-'
   ;

DOUBLE_QUOTE
   : '"'
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

OPEN_SQUARE_BRACE
   : '['
   ;

PERCENT
   : '%'
   ;

PERIOD
   : '.'
   ;

PIPE
   : '|'
   ;

SEMICOLON
   : ';'
   ;

SINGLE_QUOTE
   : '\''
   ;

SLASH
   : '/'
   ;

SPACE
   : ' '
   ;

STAR
   : '*'
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

