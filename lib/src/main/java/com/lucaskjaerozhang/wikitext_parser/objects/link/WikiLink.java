package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.objects.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.objects.sections.Text;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * Address links to other wikis.
 *
 * Why can't this just be a normal link?
 * Because you don't have to specify the wiki if you're linking within the same wiki,
 * and we don't have that information.
 */
public class WikiLink extends WikiTextParentNode {
  private final WikiLinkTarget linkTarget;

  public WikiLink(WikiLinkTarget linkTarget, String linkText) {
    super(List.of(new Text(linkText)));
    this.linkTarget = linkTarget;
  }

  @Override
  public List<NodeAttribute> getAttributes() {
    NodeAttribute article = new NodeAttribute("article", linkTarget.article(), false);
    Optional<NodeAttribute> language =
        linkTarget.language().map(l -> new NodeAttribute("language", l, false));
    Optional<NodeAttribute> section =
        linkTarget.section().map(s -> new NodeAttribute("section", s, false));
    Optional<NodeAttribute> wiki = linkTarget.wiki().map(w -> new NodeAttribute("wiki", w, false));

    return Stream.of(Optional.of(article), language, section, wiki)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  @Override
  public String getXMLTag() {
    return "wikilink";
  }

  public static String getAutomaticallyReformattedDisplayName(WikiLinkTarget target) {
    if (target.section().isPresent()) return target.wholeLink();
    return target.article().replaceAll("\\(.*\\)", "").replaceAll(",.*", "").trim();
  }
}
