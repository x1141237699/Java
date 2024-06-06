package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class UserGroupVO {
    private String id;
    private String group;

    public UserGroupVO() {
    }

    public UserGroupVO(String id, String group) {
        this.id = id;
        this.group = group;
    }
}
