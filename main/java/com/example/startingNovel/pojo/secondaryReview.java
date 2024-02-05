package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class secondaryReview {

    private int bookReviewId;

    private String content;

    public secondaryReview() {
    }

    public secondaryReview(int bookReviewId, String content) {
        this.bookReviewId = bookReviewId;
        this.content = content;
    }
}
