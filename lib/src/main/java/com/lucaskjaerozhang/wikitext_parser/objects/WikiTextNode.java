package com.lucaskjaerozhang.wikitext_parser.objects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

@JsonTypeInfo(use=DEDUCTION)
public interface WikiTextNode {
  public String getType();
}
