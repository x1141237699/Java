package com.xiao.oasystem.pojo.DTO;

import com.xiao.oasystem.pojo.entity.ApplicationForDepartment;
import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import com.xiao.oasystem.pojo.entity.ApplicationForHoliday;
import lombok.Data;

@Data
public class ApplicationDTO {
    public static final int APPLICATION_FOR_HOLIDAY = 1;
    public static final int APPLICATION_FOR_GROUP = 2;
    public static final int APPLICATION_FOR_DEPARTMENT = 3;


    private String id;
    private String initiator;
    private int variety;

    public ApplicationDTO() {
    }

    public ApplicationDTO(ApplicationForDepartment application){
        this.id = application.getId();
        this.initiator = application.getInitiator();
        this.variety = APPLICATION_FOR_DEPARTMENT;
    }

    public ApplicationDTO(ApplicationForGroup application){
        this.id = application.getId();
        this.initiator = application.getInitiator();
        this.variety = APPLICATION_FOR_GROUP;
    }

    public ApplicationDTO(ApplicationForHoliday application){
        this.id = application.getId();
        this.initiator = application.getInitiator();
        this.variety = APPLICATION_FOR_HOLIDAY;
    }

    public ApplicationDTO(String id, String initiator, int variety) {
        this.id = id;
        this.initiator = initiator;
        this.variety = variety;
    }
}
