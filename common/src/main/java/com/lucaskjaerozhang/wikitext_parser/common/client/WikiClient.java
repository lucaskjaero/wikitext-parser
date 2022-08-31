package com.lucaskjaerozhang.wikitext_parser.common.client;

import com.lucaskjaerozhang.wikitext_parser.common.client.responses.WikiPage;
import java.util.Optional;

public interface WikiClient {
  Optional<WikiPage> getPageSource(String pageTitle);
}
