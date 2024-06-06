package com.xiao.oasystem.pojo.VO.Register;

import lombok.Data;

@Data
public class GroupRegisterVo {
    private String name;
    private String content;
    private String department;

    public GroupRegisterVo() {
    }

    public GroupRegisterVo(String name, String content, String department) {
        this.name = name;
        this.content = content;
        this.department = department;
    }
}
