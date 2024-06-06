package com.xiao.oasystem.pojo.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class Record implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;
    private String message;
    private Date date;

    public Record() {
    }

    public Record(String userId, String message, Date date) {
        this.userId = userId;
        this.message = message;
        this.date = date;
    }
}
