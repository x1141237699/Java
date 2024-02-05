package com.example.startingNovel.pojo;

import lombok.Data;

@Data
public class user {

    private String userName;

    private int userAccount;

    private int userPassword;

    public user() {
    }

    public user(String userName, int userAccount, int userPassword) {
        this.userName = userName;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }

    public void showInitialInterface(){

        System.out.println("1.查看个人信息");
        System.out.println("2.查看分类");
        System.out.println("3.查看排行榜");
        System.out.println("4.搜索书籍");

    }

}
