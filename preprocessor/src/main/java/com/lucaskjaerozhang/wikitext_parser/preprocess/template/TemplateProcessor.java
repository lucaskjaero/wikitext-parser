package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateProcessor {
  private static final Pattern NO_INCLUDE_REGEX =
      Pattern.compile("<noinclude>.*?</noinclude>", Pattern.DOTALL);
  private static final Pattern ONLY_INCLUDE_REGEX =
      Pattern.compile(".*?<onlyinclude>(.*?)</onlyinclude>.*", Pattern.DOTALL);
  private static final Pattern NAMED_PARAMETER_REGEX = Pattern.compile("([^=]+)=(.*)");
  private static final Pattern REDIRECT_REGEX = Pattern.compile("#REDIRECT \\[\\[([^]]+)]].*");

  private static final TemplateParameterSubstituter substituter =
      new TemplateParameterSubstituter();

  /**
   * Processes templates in two steps:<br>
   *
   * <ol>
   *   <li>Replaces the template parameter placeholders with provided values
   *   <li>Runs the preprocessor on the template, starting the process over.
   * </ol>
   *
   * <br>
   * At the end there should be no template related things at all.<br>
   *
   * <p>If templates refer to each other in a circular way, eg: a -> b -> a, then we can recurse
   * infinitely. To prevent this, we keep a stack of which templates we have visited, and throw an
   * error if we detect this happening.
   *
   * @param templateName The template that we want to resolve.
   * @param provider A class that can get the template for the name.
   * @param visitedTemplates A stack of templates, used to prevent infinite recursion.
   * @param parameters The template parameters passed to the template.
   * @return The fully evaluated template.
   */
  public String processTemplate(
      String templateName,
      TemplateProvider provider,
      List<String> visitedTemplates,
      List<String> parameters) {
    if (visitedTemplates.contains(templateName)) {
      throw new IllegalArgumentException(
          String.format(
              "Template %s depends on a template that depends on %s, it's impossible to resolve this template. Resolution chain: %s",
              templateName, templateName, String.join(" -> ", visitedTemplates)));
    }
    List<String> visited = new ArrayList<>(visitedTemplates);
    visited.add(templateName);

    String template =
        selectPortionsForTransclusion(
            getTemplate(provider, templateName)
                .orElseThrow(
                    () ->
                        new IllegalStateException(
                            String.format("Unable to resolve template %s", templateName))));
    String substituted = parameters.isEmpty() ? template : evaluateParameters(template, parameters);

    Preprocessor preprocessor =
        Preprocessor.builder()
            .variables(
                Map.of("PAGENAME", templateName, "NAMESPACE", "Template", "NAMESPACEE", "Template"))
            .calledBy(visited)
            .templateProvider(provider)
            .templateProcessor(this)
            .build();
    return preprocessor.preprocess(substituted, true);
  }

  /**
   * Addresses includeonly, onlyinclude, and noinclude blocks.
   *
   * @param input The full template input.
   * @return The portion of the template that will be transcluded.
   */
  private static String selectPortionsForTransclusion(String input) {
    // If there is an <onlyinclude>...</onlyinclude> block, then we should only transclude that
    // part.
    Matcher matcher = ONLY_INCLUDE_REGEX.matcher(input);
    String transclusionSection = matcher.matches() ? matcher.group(1) : input;

    // Blocks with <noinclude>...</noinclude> should not be included.
    return NO_INCLUDE_REGEX
        .matcher(transclusionSection)
        .replaceAll("")
        // Blocks with <includeonly>...</includeonly> are only shown during transclusion... aka now.
        .replace("<includeonly>", "")
        .replace("</includeonly>", "");
  }

  private Optional<String> getTemplate(TemplateProvider provider, String templateName) {
    String templatePath = String.format("Template:%s", templateName);
    return provider
        .getTemplate(templatePath)
        .or(() -> provider.getTemplate(templateName))
        .flatMap(
            template -> {
              // Address redirects
              Matcher redirect = REDIRECT_REGEX.matcher(template);
              if (redirect.matches() && redirect.groupCount() >= 1) {
                String redirectedTemplateName = redirect.group(1);

                // Make sure to avoid infinite recursion if something redirects to itself.
                if (redirectedTemplateName.equals(templateName)
                    || redirectedTemplateName.equals(templatePath)) {
                  throw new IllegalArgumentException(
                      String.format("Template %s redirects to itself: %s", templateName, template));
                } else {
                  return getTemplate(provider, redirectedTemplateName);
                }
              }

              return Optional.of(template);
            });
  }

  /**
   * Creates a replacement table with the named and positional templates, and then invokes the
   * parameter substituter to do the actual substitution.
   *
   * @param template The text of the template which will have parameters substituted into it.
   * @param parameters The parameters as a list, of the form List('positional', named=value);
   * @return The template input with templates substituted.
   */
  private String evaluateParameters(String template, List<String> parameters) {
    Map<Boolean, List<String>> filteredParams =
        parameters.stream()
            .collect(Collectors.partitioningBy(p -> NAMED_PARAMETER_REGEX.asPredicate().test(p)));

    Map<String, String> namedParameters =
        filteredParams.get(true).stream()
            .map(param -> NAMED_PARAMETER_REGEX.matcher(param).results().toList().get(0))
            .collect(Collectors.toMap(r -> r.group(1), r -> r.group(2)));

    List<String> positionalParameters = filteredParams.get(false);

    return substituter.evaluateTemplate(template, positionalParameters, namedParameters);
  }
}
