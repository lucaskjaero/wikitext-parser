package com.lucaskjaerozhang.wikitext_parser.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lucaskjaerozhang.wikitext_parser.objects.Article;

import java.io.File;
import java.io.IOException;

public class XMLWriter {
    static XmlMapper xmlMapper = buildMapper();

    public static XmlMapper buildMapper() {
        XmlMapper mapper = new XmlMapper();
//        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
        return mapper;
    }

    public static String writeArticleToString(Article article) {
        try {
            return xmlMapper.writeValueAsString(article);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to write article to XML", e);
        }
    }

    public void writeArticleToFile(Article article, String filePath) {
        try {
            xmlMapper.writeValue(new File(filePath), article);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
