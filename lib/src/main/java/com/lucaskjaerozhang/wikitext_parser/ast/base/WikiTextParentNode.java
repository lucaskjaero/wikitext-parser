package com.lucaskjaerozhang.wikitext_parser.ast.base;

import com.lucaskjaerozhang.wikitext_parser.ast.sections.Text;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A node that has child nodes. This abstract class contains the logic for serializing the node into
 * XML.
 */
public abstract class WikiTextParentNode extends WikiTextNode {
  private final List<WikiTextNode> children;

  /**
   * Constructs the node
   *
   * @param children The child nodes
   */
  protected WikiTextParentNode(List<WikiTextNode> children) {
    this.children = combineTextNodes(children);
  }

  private List<WikiTextNode> combineTextNodes(List<WikiTextNode> children) {
    List<WikiTextNode> newList = new ArrayList<>(children.size());
    for (int i = 0; i < children.size(); i++) {
      // If we're in the last item, we don't want to go outside the array bounds.
      WikiTextNode first = children.get(i);
      if (i < children.size() - 1) {
        WikiTextNode second = children.get(i + 1);
        if (first instanceof Text a && second instanceof Text b) {
          Text c = new Text(a.getContent().concat(b.getContent()));
          newList.add(c);
          i++;
          continue;
        }
      }
      newList.add(first);
    }
    return newList;
  }

  /**
   * Gets the child nodes of this tree node.
   *
   * @return All children in parse order.
   */
  public List<WikiTextNode> getChildren() {
    return children;
  }

  @Override
  public Set<String> getCategories() {
    return getFieldValuesFromChildren(children, WikiTextElement::getCategories);
  }

  @Override
  public Set<String> getTemplates() {
    return getFieldValuesFromChildren(children, WikiTextElement::getTemplates);
  }

  /**
   * There is data that flow up to the root from leaf nodes. In this case we can think of it as the
   * union of the child node data.
   *
   * @param children The child elements to get data from.
   * @param getter The function that gets the data
   * @return All data from the child elements.
   */
  public static Set<String> getFieldValuesFromChildren(
      List<WikiTextNode> children, Function<? super WikiTextNode, Set<String>> getter) {
    return children.stream().map(getter).flatMap(Collection::stream).collect(Collectors.toSet());
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    getAttributes().forEach(a -> a.passProps(context));
    this.children.forEach(a -> a.passProps(context));
  }
}
