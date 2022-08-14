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

    NodeAttribute target = new NodeAttribute("target", linkTarget.target(), false);
    Optional<NodeAttribute> language =
        linkTarget.language().map(l -> new NodeAttribute("language", l, false));
    Optional<NodeAttribute> wiki = linkTarget.wiki().map(w -> new NodeAttribute("wiki", w, false));

    attributes =
        Stream.of(Optional.of(target), language, wiki)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
  }

  @Override
  public String getXMLTag() {
    return "wikilink";
  }

  @Override
  public String toXML() {
    return String.format(
        "<%s %s>%s</%s>",
        getXMLTag(), NodeAttribute.makeAttributesString(attributes), linkText, getXMLTag());
  }
}
