package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class GitProgressVO {
    private String id;
    private int progress;

    public GitProgressVO() {
    }

    public GitProgressVO(String id, int progress) {
        this.id = id;
        this.progress = progress;
    }
}
