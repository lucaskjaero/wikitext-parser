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
   | SLASH
   ;

templateParameter
   : PIPE templateParameterKeyValues* # UnnamedParameter
   | PIPE templateParameterKeyValues+ EQUALS templateParameterParameterValues* # NamedParameter
   ;

templateParameterKeyValues
   : link
   | template
   | parserFunction
   | unresolvedTemplateParameter
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
   | unresolvedTemplateParameter
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
   | PERIOD
   ;

preprocessorDirective
   : behaviorSwitch
   | parserFunction
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE TEXT UNDERSCORE UNDERSCORE
   ;

parserFunction
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE substitutionModifier? parserFunctionName COLON substitutionModifier? parserFunctionParameter (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # RegularParserFunction
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE substitutionModifier? parserFunctionName COLON substitutionModifier? (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # ParserFunctionWithBlankFirstParameter
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

substitutionModifier
   : 'safesubst' COLON (OPEN_CARAT 'noinclude' SPACE* SLASH? CLOSE_CARAT)?
   | 'SAFESUBST' COLON (OPEN_CARAT 'noinclude' SPACE* SLASH? CLOSE_CARAT)?
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
   | unresolvedTemplateParameter
   | parserFunction
   | template
   | behaviorSwitch
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

