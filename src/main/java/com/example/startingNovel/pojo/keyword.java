package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class keyword {

    private int keywordId;

    private String content;

    public keyword() {
    }

    public keyword(int keywordId, String content) {
        this.keywordId = keywordId;
        this.content = content;
    }

}
