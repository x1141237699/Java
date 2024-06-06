package com.xiao.oasystem.pojo.entity;

import lombok.Data;

@Data
public class Department {
    private String id;
    private String name;
    private String announcement;

    public Department() {
    }

    public Department(String id, String name, String announcement) {
        this.id = id;
        this.name = name;
        this.announcement = announcement;
    }
}
