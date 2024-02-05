package com.example.startingNovel.service;

import com.example.startingNovel.pojo.keyword;

import java.util.List;

public interface keywordService {

    keyword getKeywordByKeywordId(int keywordId);

    keyword getKeywordByContent(String content);

    List<keyword> getAllKeyword();
}
