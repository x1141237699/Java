package com.xiao.oasystem.pojo.DTO;

import lombok.Data;

@Data
public class GroupDTO {
    private String id;
    private String name;

    public GroupDTO() {
    }

    public GroupDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
