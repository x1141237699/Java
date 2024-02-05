package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.secondaryReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface secondaryReviewMapper {

    void setSecondaryReview(@Param("bookReviewId") int bookReviewId, @Param("content") String content);

    List<secondaryReview> getSecondaryReviewByBookReviewId(@Param("bookReviewId") int bookReviewId);

    List<secondaryReview> getAllSecondaryReview();
}
