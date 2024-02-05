package com.example.startingNovel.service.impl;

import com.example.startingNovel.exception.userAccountRepeatException;
import com.example.startingNovel.mapper.userMapper;
import com.example.startingNovel.pojo.user;
import com.example.startingNovel.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private userMapper usermapper;

    @Override
    public void setUser(String userName, int userAccount, int userPassword) {
        List<user> list = usermapper.getAllUser();
        for (user user : list) {
            if (user.getUserAccount() == userAccount)
                throw new userAccountRepeatException("账号" + userAccount + "已存在");
        }
        usermapper.setUser(userName,userAccount,userPassword);
    }

    @Override
    public Boolean changeUserName(int userAccount, String userName) {
        usermapper.changeUserName(userAccount,userName);
        return Boolean.TRUE;
    }

    @Override
    public user getUserByUserAccount(int userAccount) {
        return usermapper.getUserByUserAccount(userAccount);
    }

    @Override
    public user getUserByUserName(String userName) {
        return usermapper.getUserByUserName(userName);
    }

    @Override
    public List<user> getAllUser() {
        return usermapper.getAllUser();
    }
}
