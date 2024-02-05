package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.history;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface historyMapper {

    void setHistory(@Param("userAccount") int userAccount,@Param("bookId") int bookId);

    void changeHistory(@Param("userAccount") int userAccount,@Param("bookId") int bookId);

    List<history> getHistoryByUserAccount(@Param("userAccount") int userAccount);

    List<history> getAllHistory();
}
