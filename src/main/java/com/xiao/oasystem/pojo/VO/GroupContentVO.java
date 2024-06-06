package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class GroupContentVO {
    private String id;
    private String content;

    public GroupContentVO() {
    }

    public GroupContentVO(String id, String content) {
        this.id = id;
        this.content = content;
    }
}
