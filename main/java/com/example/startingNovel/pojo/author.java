package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class author {

    private int authorId;

    private String authorName;

    private String authorSynopsis;

    public author() {
    }

    public author(int authorId, String authorName,String authorSynopsis) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorSynopsis = authorSynopsis;
    }

}
