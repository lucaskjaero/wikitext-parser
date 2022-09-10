package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

import com.lucaskjaerozhang.wikitext_parser.common.StringEqualityTester;
import com.lucaskjaerozhang.wikitext_parser.preprocess.Preprocessor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateProcessor {
  private static final Pattern NO_INCLUDE_REGEX =
      Pattern.compile("<noinclude>.*?</noinclude>", Pattern.DOTALL);
  private static final Pattern ONLY_INCLUDE_REGEX =
      Pattern.compile(".*?<onlyinclude>(.*?)</onlyinclude>.*", Pattern.DOTALL);
  private static final Pattern REDIRECT_REGEX =
      Pattern.compile("#REDIRECT \\[\\[([^]]+)]].*", Pattern.DOTALL);

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
   * @param positionalParameters The parameters as a list, of the form List('positional');
   * @param namedParameters The parameters as a list, of the form List('named=value');
   * @return The fully evaluated template.
   */
  public String processTemplate(
      String templateName,
      TemplateProvider provider,
      List<String> visitedTemplates,
      List<String> positionalParameters,
      Map<String, String> namedParameters) {
    List<String> visited = new ArrayList<>(visitedTemplates);
    visited.add(templateName);
    detectInfiniteRecursion(visited);

    String template =
        selectPortionsForTransclusion(
            getTemplate(provider, templateName)
                .orElseThrow(
                    () ->
                        new IllegalStateException(
                            String.format("Unable to resolve template %s", templateName))));

    // This always needs to happen to deal with default parameters
    String substituted =
        substituter.evaluateTemplate(template, positionalParameters, namedParameters);

    Preprocessor preprocessor =
        Preprocessor.builder()
            .variables(
                Map.of(
                    "PAGENAME",
                    templateName,
                    "PAGENAMEE",
                    templateName,
                    "NAMESPACE",
                    "Template",
                    "NAMESPACEE",
                    "Template",
                    "TALKPAGENAME",
                    "TALKPAGENAME",
                    "NAMESPACENUMBER",
                    "0"))
            .calledBy(visited)
            .templateProvider(provider)
            .templateProcessor(this)
            .build();
    return preprocessor.preprocess(substituted, true);
  }

  private void detectInfiniteRecursion(List<String> visitedTemplates) {
    // A template can call itself once, but no more. This guards against that.
    if (visitedTemplates.size() >= 3) {
      String twoAgo = visitedTemplates.get(visitedTemplates.size() - 3);
      String oneAgo = visitedTemplates.get(visitedTemplates.size() - 2);
      String current = visitedTemplates.get(visitedTemplates.size() - 1);

      if (StringEqualityTester.equalsIgnoreCaseExceptFirstLetter(twoAgo, oneAgo)
          && StringEqualityTester.equalsIgnoreCaseExceptFirstLetter(oneAgo, current)) {
        throw new IllegalArgumentException(
            String.format(
                "Template %s has depended on itself twice, meaning it's impossible to resolve this template without an infinite recursion. Resolution chain: %s",
                current, String.join(" -> ", visitedTemplates)));
      }
    }

    if (visitedTemplates.size() >= 10) {
      throw new IllegalArgumentException(
          String.format(
              "Reached recursion limit resolving templates. Resolution chain: %s",
              String.join(" -> ", visitedTemplates)));
    }
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
}
