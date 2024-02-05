package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class book {

    private String bookName;

    private int bookId;

    private int bookPage;

    private String authorName;

    private String synopsis;

    private Data updateTime;

    private String catalogue;

    public book() {
    }

    public book(String bookName, int bookId, int bookPage, String authorName, String synopsis, Data updateTime, String catalogue) {
        this.bookName = bookName;
        this.bookId = bookId;
        this.bookPage = bookPage;
        this.authorName = authorName;
        this.synopsis = synopsis;
        this.updateTime = updateTime;
        this.catalogue = catalogue;
    }

}
