package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class bookReview {

    private int bookId;

    private String content;

    public bookReview() {
    }

    public bookReview(int bookId, String content) {
        this.bookId = bookId;
        this.content = content;
    }

}
