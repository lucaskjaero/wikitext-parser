grammar WikiTextPreprocessor;

root
   : element+
   ;

element
   : nowikiBlock
   | unresolvedTemplateParameter
   | behaviorSwitch
   | parserFunction
   | template
   | any
   ;

elementNoAny
   : nowikiBlock
   | unresolvedTemplateParameter
   | behaviorSwitch
   | parserFunction
   | template
   ;

nowikiBlock
   : OPEN_CARAT 'nowiki' CLOSE_CARAT .+? OPEN_CARAT SLASH 'nowiki' CLOSE_CARAT
   | OPEN_CARAT 'code' CLOSE_CARAT .+? OPEN_CARAT SLASH 'code' CLOSE_CARAT
   ;

unresolvedTemplateParameter
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateParameterName+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateParameterWithoutDefault
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateParameterName+ PIPE? element*? CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateParameterWithDefault
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE PIPE element*? CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateParameterWithBlankName
   ;

templateParameterName
   : reservedLiteral
   | ANY
   | CLOSE_CARAT
   | CLOSE_SQUARE_BRACE
   | COLON
   | DASH
   | EQUALS
   | EXCLAMATION_MARK
   | OPEN_CARAT
   | OPEN_CURLY_BRACE
   | OPEN_SQUARE_BRACE
   | SLASH
   | UNDERSCORE
   ;

template
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithNoParameters
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ templateParameter+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithParameters
   ;

templateName
   : reservedLiteral
   | ANY
   | CLOSE_CARAT
   | CLOSE_SQUARE_BRACE
   | DASH
   | EQUALS
   | EXCLAMATION_MARK
   | OPEN_CARAT
   | OPEN_SQUARE_BRACE
   | SLASH
   | UNDERSCORE
   ;

templateParameter
   : PIPE templateParameterKeyValues* # UnnamedParameter
   | PIPE templateParameterKeyValues+ EQUALS templateParameterParameterValues* # NamedParameter
   ;

templateParameterKeyValues
   : link
   | elementNoAny
   | reservedLiteral
   | ANY
   | CLOSE_CARAT
   | CLOSE_SQUARE_BRACE
   | COLON
   | DASH
   | EXCLAMATION_MARK
   | OPEN_CURLY_BRACE
   | OPEN_CARAT
   | OPEN_SQUARE_BRACE
   | SLASH
   | UNDERSCORE
   ;

templateParameterParameterValues
   : link
   | elementNoAny
   | reservedLiteral
   | ANY
   | CLOSE_CARAT
   | CLOSE_SQUARE_BRACE
   | COLON
   | DASH
   | EQUALS
   | EXCLAMATION_MARK
   | OPEN_CARAT
   | OPEN_CURLY_BRACE
   | OPEN_SQUARE_BRACE
   | SLASH
   | UNDERSCORE
   ;

link
   : OPEN_SQUARE_BRACE OPEN_SQUARE_BRACE linkNamespaceComponent* linkTarget+ (PIPE linkText+)? CLOSE_SQUARE_BRACE CLOSE_SQUARE_BRACE
   ;

linkNamespaceComponent
   : ANY+ COLON
   ;

linkTarget
   : ANY+
   | DASH
   ;

linkText
   : elementNoAny
   | reservedLiteral
   | ANY
   | CLOSE_CARAT
   | COLON
   | DASH
   | EQUALS
   | EXCLAMATION_MARK
   | OPEN_CARAT
   | OPEN_CURLY_BRACE
   | OPEN_SQUARE_BRACE
   | PIPE
   | SLASH
   | UNDERSCORE
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE behaviorSwitchName UNDERSCORE UNDERSCORE
   ;

behaviorSwitchName
   : (~ UNDERSCORE)+
   ;

parserFunction
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionPrefix parserFunctionParameter (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # RegularParserFunction
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE parserFunctionPrefix (PIPE parserFunctionParameter)* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # ParserFunctionWithBlankFirstParameter
   ;

parserFunctionPrefix
   : substitutionModifier parserFunctionName COLON substitutionModifier?
   | parserFunctionName COLON substitutionModifier?
   ;

parserFunctionName
   : parserFunctionCharacters+
   ;

parserFunctionCharacters
   : reservedLiteral
   | ANY
   | CLOSE_CARAT
   | DASH
   | EQUALS
   | EXCLAMATION_MARK
   | OPEN_CURLY_BRACE
   | OPEN_CARAT
   | SLASH
   | UNDERSCORE
   ;

substitutionModifier
   : SAFESUBST COLON (OPEN_CARAT NOINCLUDE SLASH? CLOSE_CARAT)?
   ;

parserFunctionParameter
   : parserFunctionParameterValues*
   ;

parserFunctionParameterValues
   : link
   | elementNoAny
   | reservedLiteral
   | ANY
   | DASH
   | CLOSE_CARAT
   | CLOSE_SQUARE_BRACE
   | COLON
   | EQUALS
   | SLASH
   | OPEN_CARAT
   | OPEN_SQUARE_BRACE
   | UNDERSCORE
   ;

any
   : .+?
   ;

reservedLiteral
   : 'nowiki'
   | 'code'
   | SAFESUBST
   | NOINCLUDE
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

DASH
   : '-'
   ;

EQUALS
   : '='
   ;

EXCLAMATION_MARK
   : '!'
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

PIPE
   : '|'
   ;

SLASH
   : '/'
   ;

UNDERSCORE
   : '_'
   ;

SAFESUBST
   : [sS] [aA] [fF] [eE] [sS] [uU] [bB] [sS] [tT]
   ;

NOINCLUDE
   : [nN] [oO] [iI] [nN] [cC] [lL] [uU] [dD] [eE] ' '?
   ;

ANY
   : .+?
   ;
