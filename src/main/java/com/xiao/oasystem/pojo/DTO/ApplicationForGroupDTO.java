package com.xiao.oasystem.pojo.DTO;

import lombok.Data;

@Data
public class ApplicationForGroupDTO {
    private String id;
    private String initiator;

    public ApplicationForGroupDTO() {
    }

    public ApplicationForGroupDTO(String id, String initiator) {
        this.id = id;
        this.initiator = initiator;
    }
}
