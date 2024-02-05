package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.rankingList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface rankingListMapper {

    List<rankingList> getNewList();

    List<rankingList> getHotList();
}
