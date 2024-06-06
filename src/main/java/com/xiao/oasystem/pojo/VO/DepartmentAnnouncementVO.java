package com.xiao.oasystem.pojo.VO;

import lombok.Data;

@Data
public class DepartmentAnnouncementVO {
    private String id;
    private String announcement;

    public DepartmentAnnouncementVO() {
    }

    public DepartmentAnnouncementVO(String id, String announcement) {
        this.id = id;
        this.announcement = announcement;
    }
}
