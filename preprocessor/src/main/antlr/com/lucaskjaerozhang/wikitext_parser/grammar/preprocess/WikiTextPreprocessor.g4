grammar WikiTextPreprocessor;

root
   : element+
   ;

element
   : nowikiBlock
   | unresolvedTemplateParameter
   | languageConversion
   | behaviorSwitch
   | parserFunction
   | template
   | externalLink
   | any
   ;

elementNoAny
   : nowikiBlock
   | unresolvedTemplateParameter
   | languageConversion
   | behaviorSwitch
   | parserFunction
   | template
   | externalLink
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
   | SPACE
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
   | SPACE
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
   | SPACE
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
   | SPACE
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
   : (ANY | SPACE)+ COLON
   ;

linkTarget
   : (ANY | SPACE)+
   | DASH
   ;

linkText
   : elementNoAny
   | reservedLiteral
   | SPACE
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

externalLink
   : OPEN_SQUARE_BRACE externalLinkStart externalLinkText* CLOSE_SQUARE_BRACE
   ;

externalLinkStart
   : HTTP COLON SLASH SLASH
   | HTTPS COLON SLASH SLASH
   | SLASH SLASH
   ;

externalLinkText
   : languageConversion
   | link
   | behaviorSwitch
   | parserFunction
   | template
   | unresolvedTemplateParameter
   | reservedLiteral
   | SPACE
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

languageConversion
   : DASH OPEN_CURLY_BRACE languageConversionText* CLOSE_CURLY_BRACE DASH
   ;

languageConversionText
   : link
   | externalLink
   | behaviorSwitch
   | parserFunction
   | template
   | unresolvedTemplateParameter
   | reservedLiteral
   | SPACE
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
   : SPACE* substitutionModifier parserFunctionName COLON substitutionModifier?
   | SPACE* parserFunctionName COLON substitutionModifier?
   ;

parserFunctionName
   : parserFunctionCharacters+
   ;

parserFunctionCharacters
   : reservedLiteral
   | SPACE
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
   | SPACE
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
   | HTTPS
   | HTTP
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

SPACE
   : [ \t\r\n]
   ;

SAFESUBST
   : [sS] [aA] [fF] [eE] [sS] [uU] [bB] [sS] [tT]
   ;

NOINCLUDE
   : [nN] [oO] [iI] [nN] [cC] [lL] [uU] [dD] [eE] ' '?
   ;

HTTPS
   : [hH] [tT] [tT] [pP] [sS]
   ;

HTTP
   : [hH] [tT] [tT] [pP]
   ;

ANY
   : .+?
   ;
