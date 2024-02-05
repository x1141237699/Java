package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.historyMapper;
import com.example.startingNovel.pojo.history;
import com.example.startingNovel.service.historyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class historyServiceImpl implements historyService {

    @Autowired
    private historyMapper historymapper;

    @Override
    public List<history> getHistoryByUserAccount(int userAccount) {
        return historymapper.getHistoryByUserAccount(userAccount);
    }

    @Override
    public void setHistory(int userAccount, int bookId) {
        historymapper.setHistory(userAccount, bookId);
    }

    @Override
    public void changeHistory(int userAccount, int bookId) {
        historymapper.changeHistory(userAccount, bookId);
    }

    @Override
    public List<history> getAllHistory() {
        return historymapper.getAllHistory();
    }
}
