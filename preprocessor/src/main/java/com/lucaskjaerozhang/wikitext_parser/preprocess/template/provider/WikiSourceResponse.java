package com.lucaskjaerozhang.wikitext_parser.preprocess.template.provider;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WikiSourceResponse {
  private List<WikiPage> items;
}
