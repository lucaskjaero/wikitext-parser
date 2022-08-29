package com.lucaskjaerozhang.wikitext_parser.preprocess.template;

/**
 * An interface for getting templates for transclusion. Allows for multiple use cases:<br>
 *
 * <ul>
 *   <li>An online fetcher that queries wikis as needed.
 *   <li>A package with common templates pre-fetched.
 *   <li>A script that works with backups of the wiki databases.
 * </ul>
 */
public interface TemplateProvider {
  String getTemplate(String template);
}
