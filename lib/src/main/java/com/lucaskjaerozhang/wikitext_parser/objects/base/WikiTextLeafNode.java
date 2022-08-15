package com.lucaskjaerozhang.wikitext_parser.objects.base;

import java.util.List;

public abstract class WikiTextLeafNode extends WikiTextNode {
  @Override
  public String toXML() {
    List<NodeAttribute> attributeList = getAttributes();
    return attributeList.isEmpty()
        ? String.format("<%s />", getXMLTag())
        : String.format(
            "<%s %s />", getXMLTag(), NodeAttribute.makeAttributesString(getAttributes()));
  }
}
