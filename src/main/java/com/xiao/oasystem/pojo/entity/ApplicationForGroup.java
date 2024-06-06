package com.xiao.oasystem.pojo.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ApplicationForGroup implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    public static final int TO_BE_SUBMITTED = 0;
    public static final int SUBMITTED = 1;
    public static final int PASS_FOR_OUT = 2;
    public static final int PASS_FOR_IN = 3;
    public static final int REFUSED = -1;

    private String id;
    private String initiator;
    private String outGroup;
    private String inGroup;
    private int state;

    public ApplicationForGroup() {
    }

    public ApplicationForGroup(String id, String initiator, String outGroup, String inGroup, int state) {
        this.id = id;
        this.initiator = initiator;
        this.outGroup = outGroup;
        this.inGroup = inGroup;
        this.state = state;
    }
}
