package com.lucaskjaerozhang.wikitext_parser.ast.link;

import com.lucaskjaerozhang.wikitext_parser.ast.base.NodeAttribute;
import com.lucaskjaerozhang.wikitext_parser.ast.base.WikiTextParentNode;
import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import com.lucaskjaerozhang.wikitext_parser.visitor.WikiTextASTVisitor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A link to a wiki page within this or other wikis.
 *
 * <p>Wikitext: [[article|display]] xml: wikilink
 *
 * <p>For more information about links you can look at WikiLinkTarget.
 *
 * <p>Why can't this just be a normal link? Because you don't have to specify the wiki if you're
 * linking within the same wiki, and we don't have that information.
 */
public class WikiLink extends WikiTextParentNode {
  private final WikiLinkTarget linkTarget;

  /**
   * Generates a wikilink.
   *
   * @param linkTarget The article the link points to.
   * @param linkText The text to display.
   */
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

  @Override
  public <T> Optional<T> accept(WikiTextASTVisitor<T> visitor) {
    return visitor.visitWikiLink(this);
  }

  /**
   * Gets a wikilink that has the display text automatically generated.
   *
   * @param target The link target.
   * @return A wikilink with an autoformatted display text.
   */
  public static String getAutomaticallyReformattedDisplayName(WikiLinkTarget target) {
    if (target.section().isPresent()) return target.wholeLink();
    return target.article().replaceAll("\\(.*\\)", "").replaceAll(",.*", "").trim();
  }
}
