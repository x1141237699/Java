package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class history {

    private int userAccount;

    private int bookId;

    private Data date;

    public history() {
    }

    public history(int userAccount, int bookId, Data date) {
        this.userAccount = userAccount;
        this.bookId = bookId;
        this.date = date;
    }
}
