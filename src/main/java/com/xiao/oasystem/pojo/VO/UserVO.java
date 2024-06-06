package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class UserVO {
    private String id;
    private String name;
    private int position;

    public UserVO() {
    }

    public UserVO(String id, String name, int position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }
}
