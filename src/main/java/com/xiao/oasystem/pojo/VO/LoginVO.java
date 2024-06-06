package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class LoginVO {
    private String id;
    private String password;

    public LoginVO() {
    }

    public LoginVO(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
