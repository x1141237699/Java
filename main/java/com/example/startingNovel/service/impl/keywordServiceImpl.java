package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.keywordMapper;
import com.example.startingNovel.pojo.keyword;
import com.example.startingNovel.service.keywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class keywordServiceImpl implements keywordService {

    @Autowired
    private keywordMapper keywordmapper;

    @Override
    public keyword getKeywordByKeywordId(int keywordId) {
        return keywordmapper.getKeywordByKeywordId(keywordId);
    }

    @Override
    public keyword getKeywordByContent(String content) {
        return keywordmapper.getKeywordByContent(content);
    }

    @Override
    public List<keyword> getAllKeyword() {
        return keywordmapper.getAllKeyword();
    }
}
