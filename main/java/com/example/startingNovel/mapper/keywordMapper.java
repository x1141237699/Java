package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.keyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface keywordMapper {

    keyword getKeywordByKeywordId(@Param("keywordId") int keywordId);

    keyword getKeywordByContent(@Param("content") String content);

    List<keyword> getAllKeyword();
}
