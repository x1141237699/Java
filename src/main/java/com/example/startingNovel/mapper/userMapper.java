package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface userMapper {

    void setUser(@Param("userName") String userName, @Param("userAccount") int userAccount, @Param("userPassword") int userPassword);

    void changeUserName(@Param("userAccount") int userAccount, @Param("userName") String userName);

    user getUserByUserAccount(@Param("userAccount") int userAccount);

    user getUserByUserName(@Param("userName") String userName);

    List<user> getAllUser();
}
