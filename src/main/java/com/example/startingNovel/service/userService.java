package com.example.startingNovel.service;

import com.example.startingNovel.pojo.user;

import java.util.List;

public interface userService {

    void setUser(String userName, int userAccount, int userPassword);

    Boolean changeUserName(int userAccount, String userName);

    user getUserByUserAccount(int userAccount);

    user getUserByUserName(String userName);

    List<user> getAllUser();
}
