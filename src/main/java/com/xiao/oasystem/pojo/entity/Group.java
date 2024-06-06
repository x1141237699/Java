package com.xiao.oasystem.pojo.entity;

import lombok.Data;

@Data
public class Group {
    public static final int UNDERWAY = 1;//正在进行
    public static final int FINISHED = 0;//已结束

    private String id;
    private String name;
    private String content;
    private int progress;
    private String department;
    private int state;

    public Group() {
    }

    public Group(String id, String name, String content, int progress, String department, int state) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.progress = progress;
        this.department = department;
        this.state = state;
    }
}
