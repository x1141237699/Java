package com.xiao.oasystem.pojo.entity;

import lombok.Data;

@Data
public class User {
    public static final int NORMAL_USER = 1;
    public static final int GROUP_MANAGER = 2;
    public static final int DEPARTMENT_MANAGER = 3;
    public static final int SUPER_MANAGER = 4;

    private String id;
    private String password;
    private String name;
    private int position;
    private String department;
    private String group;

    public User() {
    }

    public User(String id, String password, String name, int position, String department, String group) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.position = position;
        this.department = department;
        this.group = group;
    }
}
