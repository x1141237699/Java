package com.example.startingNovel.controller;

import com.example.startingNovel.exception.userAccountRepeatException;
import com.example.startingNovel.pojo.user;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.userServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
public class userController {

    @Autowired
    private userServiceImpl userserviceimpl;

    @GetMapping("/setUser")
    public result<Boolean> setUser(@RequestParam String userName, @RequestParam int userAccount, @RequestParam int userPassword){
        try {
            userserviceimpl.setUser(userName,userAccount,userPassword);
        }catch (userAccountRepeatException e){
            return result.fail(Boolean.FALSE);
        }
        return result.success(Boolean.TRUE);
    }

    @GetMapping("/changeUserName")
    public result<Boolean> changeUserName(@RequestParam int userAccount, @RequestParam String userName){
        if(userserviceimpl.changeUserName(userAccount,userName))
            return result.fail(userserviceimpl.changeUserName(userAccount,userName));
        return result.success(userserviceimpl.changeUserName(userAccount,userName));
    }

    @GetMapping("/getUserByUserAccount")
    public result<user> getUserByUserAccount(@RequestParam int userAccount) {
        if(userserviceimpl.getUserByUserAccount(userAccount) == null)
            return result.fail(userserviceimpl.getUserByUserAccount(userAccount));
        return result.success(userserviceimpl.getUserByUserAccount(userAccount));
    }

    @GetMapping("/getUserByUserName")
    public result<user> getUserByUserName(@RequestParam String userName) {
        if(userserviceimpl.getUserByUserName(userName) == null)
            return result.fail(userserviceimpl.getUserByUserName(userName));
        return result.success(userserviceimpl.getUserByUserName(userName));
    }

    @GetMapping("/list")
    public result<List<user>> getAllUser() {
        if(userserviceimpl.getAllUser().isEmpty())
            return result.fail(userserviceimpl.getAllUser());
        return result.success(userserviceimpl.getAllUser());
    }
}
