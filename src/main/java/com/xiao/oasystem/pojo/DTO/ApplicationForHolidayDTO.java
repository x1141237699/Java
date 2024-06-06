package com.xiao.oasystem.pojo.DTO;

import lombok.Data;

@Data
public class ApplicationForHolidayDTO {
    private String id;
    private String initiator;

    public ApplicationForHolidayDTO() {
    }

    public ApplicationForHolidayDTO(String id, String initiator) {
        this.id = id;
        this.initiator = initiator;
    }
}
