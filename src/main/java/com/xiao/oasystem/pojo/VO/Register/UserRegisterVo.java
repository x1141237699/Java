package com.xiao.oasystem.pojo.VO.Register;

import lombok.Data;

@Data
public class UserRegisterVo {
    private String password;
    private String name;
    private int position;
    private String department;
    private String group;

    public UserRegisterVo() {
    }

    public UserRegisterVo(String password, String name, int position, String department, String group) {
        this.password = password;
        this.name = name;
        this.position = position;
        this.department = department;
        this.group = group;
    }
}
