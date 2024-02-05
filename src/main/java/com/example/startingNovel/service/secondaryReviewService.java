package com.example.startingNovel.service;

import com.example.startingNovel.pojo.secondaryReview;

import java.util.List;

public interface secondaryReviewService {

    Boolean setSecondaryReview(int bookReviewId, String content);

    List<secondaryReview> getSecondaryReviewByBookReviewId(int bookReviewId);

    List<secondaryReview> getAllSecondaryReview();
}
