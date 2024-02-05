package com.example.startingNovel.service;

import com.example.startingNovel.pojo.bookReview;

import java.util.List;

public interface bookReviewService {
    Boolean setBookReview(int bookId,String content);

    bookReview getBookReviewByBookId(int bookId);

    List<bookReview> getAllBookReview();

}
