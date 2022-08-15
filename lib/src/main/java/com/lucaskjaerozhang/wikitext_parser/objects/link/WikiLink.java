package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.objects.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
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
public class WikiLink implements WikiTextNode {
  private final String linkText;
  private final List<NodeAttribute> attributes;

  public WikiLink(WikiLinkTarget linkTarget, String linkText) {
    this.linkText = linkText;

    NodeAttribute article = new NodeAttribute("article", linkTarget.article(), false);
    Optional<NodeAttribute> language =
        linkTarget.language().map(l -> new NodeAttribute("language", l, false));
    Optional<NodeAttribute> section =
        linkTarget.section().map(s -> new NodeAttribute("section", s, false));
    Optional<NodeAttribute> wiki = linkTarget.wiki().map(w -> new NodeAttribute("wiki", w, false));

    attributes =
        Stream.of(Optional.of(article), language, section, wiki)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
  }

  public String getAttributesString() {
    return NodeAttribute.makeAttributesString(attributes);
  }

  @Override
  public String getXMLTag() {
    return "wikilink";
  }

  @Override
  public String toXML() {
    return String.format(
        "<%s %s>%s</%s>", getXMLTag(), getAttributesString(), linkText, getXMLTag());
  }

  public static String getAutomaticallyReformattedDisplayName(WikiLinkTarget target) {
    if (target.section().isPresent()) return target.wholeLink();
    return target.article().replaceAll("\\(.*\\)", "").replaceAll(",.*", "").trim();
  }
}
