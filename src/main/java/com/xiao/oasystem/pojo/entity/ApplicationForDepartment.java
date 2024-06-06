package com.xiao.oasystem.pojo.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ApplicationForDepartment implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    public static final int TO_BE_SUBMITTED = 0;
    public static final int SUBMITTED = 1;
    public static final int PASS_FOR_OUT = 2;
    public static final int PASS_FOR_IN = 3;
    public static final int REFUSED = -1;

    private String id;
    private String initiator;
    private String outDepartment;
    private String inDepartment;
    private int state;

    public ApplicationForDepartment() {
    }

    public ApplicationForDepartment(String id, String initiator, String outDepartment, String inDepartment, int state) {
        this.id = id;
        this.initiator = initiator;
        this.outDepartment = outDepartment;
        this.inDepartment = inDepartment;
        this.state = state;
    }
}
