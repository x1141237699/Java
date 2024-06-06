package com.xiao.oasystem.pojo.DTO;

import lombok.Data;

@Data
public class ApplicationForDepartmentDTO {
    private String id;
    private String initiator;

    public ApplicationForDepartmentDTO() {
    }

    public ApplicationForDepartmentDTO(String id, String initiator) {
        this.id = id;
        this.initiator = initiator;
    }
}
