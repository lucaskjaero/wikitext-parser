package com.lucaskjaerozhang.wikitext_parser.ast.base;

import java.util.List;

/**
 * A node that does not have any children, representing the bottom of the AST. Handles
 * serialization.
 */
public abstract class WikiTextLeafNode extends WikiTextNode {
  @Override
  public String toXML() {
    List<NodeAttribute> attributeList = getAttributes();
    return attributeList.isEmpty()
        ? String.format("<%s />", getXMLTag())
        : String.format(
            "<%s %s />", getXMLTag(), NodeAttribute.makeAttributesString(getAttributes()));
  }

  @Override
  public void passProps(TreeConstructionContext context) {
    getAttributes().forEach(a -> a.passProps(context));
  }
}
