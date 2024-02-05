package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.rankingListMapper;
import com.example.startingNovel.pojo.rankingList;
import com.example.startingNovel.service.rankingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class rankingListServiceImpl implements rankingListService {

    @Autowired
    private rankingListMapper rankinglistmapper;

    @Override
    public List<rankingList> getNewList() {
        return rankinglistmapper.getNewList();
    }

    @Override
    public List<rankingList> getHotList() {
        return rankinglistmapper.getHotList();
    }
}
