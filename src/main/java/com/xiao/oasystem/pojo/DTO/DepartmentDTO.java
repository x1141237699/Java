package com.xiao.oasystem.pojo.DTO;

import lombok.Data;

@Data
public class DepartmentDTO {
    private String id;
    private String name;

    public DepartmentDTO() {
    }

    public DepartmentDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
