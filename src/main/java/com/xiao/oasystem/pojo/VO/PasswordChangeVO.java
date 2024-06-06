package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class PasswordChangeVO {
    private String oldPassword;
    private String newPassword;

    public PasswordChangeVO() {
    }

    public PasswordChangeVO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
