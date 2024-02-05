package com.example.startingNovel.service;

import com.example.startingNovel.pojo.rankingList;

import java.util.List;

public interface rankingListService {

    List<rankingList> getNewList();

    List<rankingList> getHotList();
}
