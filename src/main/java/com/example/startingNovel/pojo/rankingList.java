package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class rankingList {

    private String bookName;

    private int rank;

    public rankingList() {
    }

    public rankingList(String bookName, int rank) {
        this.bookName = bookName;
        this.rank = rank;
    }
}
