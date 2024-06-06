package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class UserDepartmentVO {
    private String id;
    private String department;

    public UserDepartmentVO() {
    }

    public UserDepartmentVO(String id, String department) {
        this.id = id;
        this.department = department;
    }
}
