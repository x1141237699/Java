package com.xiao.oasystem.pojo.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String name;

    public UserDTO() {
    }

    public UserDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
