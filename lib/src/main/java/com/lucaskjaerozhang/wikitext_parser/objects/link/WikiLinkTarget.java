package com.lucaskjaerozhang.wikitext_parser.objects.link;

import com.lucaskjaerozhang.wikitext_parser.objects.WikiTextNode;
import java.util.Optional;

public record WikiLinkTarget(Optional<String> wiki, Optional<String> language, String target)
    implements WikiTextNode {
  @Override
  public String getXMLTag() {
    return "linktarget";
  }

  @Override
  public String toXML() {
    throw new UnsupportedOperationException("Intermediate type");
  }
}
