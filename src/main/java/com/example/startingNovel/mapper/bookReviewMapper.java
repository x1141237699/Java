package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.bookReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface bookReviewMapper {

    void setBookReview(@Param("bookId") int bookId,@Param("content") String content);

    bookReview getBookReviewByBookId(@Param("bookId") int bookId);

    List<bookReview> getAllBookReview();
}
