package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import java.util.List;
import java.util.Map;

public record TemplateInvocation(
    String templateName, List<String> positionalParameters, Map<String, String> namedParameters) {}
