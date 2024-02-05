package com.example.startingNovel.service;

import com.example.startingNovel.pojo.history;

import java.util.List;

public interface historyService {

    List<history> getHistoryByUserAccount(int userAccount);

    void setHistory(int userAccount, int bookId);

    void changeHistory(int userAccount, int bookId);

    List<history> getAllHistory();
}
