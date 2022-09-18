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

nowikiBlock
   : OPEN_CARAT 'nowiki' CLOSE_CARAT .*? OPEN_CARAT 'nowiki' SLASH CLOSE_CARAT
   ;

unresolvedTemplateParameter
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateParameterName CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateParameterWithoutDefault
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateParameterName PIPE? element* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateParameterWithDefault
   ;

templateParameterName
   : ANY
   | CLOSE_CARAT
   | COLON
   | DASH
   | EQUALS
   | EXCLAMATION_MARK
   | OPEN_CURLY_BRACE
   | OPEN_CARAT
   | SLASH
   | UNDERSCORE
   ;

template
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithNoParameters
   | OPEN_CURLY_BRACE OPEN_CURLY_BRACE templateName+ templateParameter+ CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE # TemplateWithParameters
   ;

templateName
   : ANY
   | CLOSE_CARAT
   | COLON
   | DASH
   | EQUALS
   | EXCLAMATION_MARK
   | OPEN_CURLY_BRACE
   | OPEN_CARAT
   | SLASH
   | UNDERSCORE
   ;

templateParameter
   : PIPE templateParameterKeyValues* # UnnamedParameter
   | PIPE templateParameterKeyValues+ EQUALS templateParameterParameterValues* # NamedParameter
   ;

templateParameterKeyValues
   : element
   | link
   | ANY
   | CLOSE_CARAT
   | COLON
   | DASH
   | EXCLAMATION_MARK
   | OPEN_CURLY_BRACE
   | OPEN_CARAT
   | SLASH
   | UNDERSCORE
   ;

templateParameterParameterValues
   : element
   | link
   | ANY
   | CLOSE_CARAT
   | COLON
   | DASH
   | EXCLAMATION_MARK
   | OPEN_CURLY_BRACE
   | OPEN_CARAT
   | PIPE
   | SLASH
   | UNDERSCORE
   ;

link
   : '[' '[' linkNamespaceComponent* linkTarget+ (PIPE element+)? ']' ']'
   ;

linkNamespaceComponent
   : ANY+ COLON
   ;

linkTarget
   : ANY+
   | DASH
   ;

behaviorSwitch
   : UNDERSCORE UNDERSCORE behaviorSwitchName UNDERSCORE UNDERSCORE
   ;

behaviorSwitchName
   : ~ UNDERSCORE
   ;

parserFunction
   : OPEN_CURLY_BRACE OPEN_CURLY_BRACE substitutionModifier? parserFunctionName COLON substitutionModifier? parserFunctionParameter* CLOSE_CURLY_BRACE CLOSE_CURLY_BRACE
   ;

parserFunctionName
   : parserFunctionCharacters+
   ;

parserFunctionCharacters
   : ANY
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
   : 'safesubst' COLON (OPEN_CARAT 'noinclude' SLASH? CLOSE_CARAT)?
   | 'SAFESUBST' COLON (OPEN_CARAT 'noinclude' SLASH? CLOSE_CARAT)?
   | 'safesubst' COLON (OPEN_CARAT 'noinclude ' SLASH? CLOSE_CARAT)?
   | 'SAFESUBST' COLON (OPEN_CARAT 'noinclude ' SLASH? CLOSE_CARAT)?
   ;

parserFunctionParameter
   : PIPE parserFunctionParameterValues*
   | parserFunctionParameterValues+
   ;

parserFunctionParameterValues
   : ANY
   | DASH
   | COLON
   | EQUALS
   | SLASH
   | OPEN_CARAT
   | CLOSE_CARAT
   | UNDERSCORE
   | element
   | link
   ;

any
   : .+?
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

PIPE
   : '|'
   ;

SLASH
   : '/'
   ;

UNDERSCORE
   : '_'
   ;

ANY
   : .+?
   ;

