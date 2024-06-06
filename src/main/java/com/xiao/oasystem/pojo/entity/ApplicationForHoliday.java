package com.xiao.oasystem.pojo.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ApplicationForHoliday implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    public static final int TO_BE_SUBMITTED = 0;
    public static final int SUBMITTED = 1;
    public static final int PASSED = 3;
    public static final int REFUSED = -1;

    public static final int GROUP = 1;
    public static final int DEPARTMENT = 2;

    private String id;
    private String initiator;
    private String recipient;
    private String reason;
    private int state;
    private int variety;

    public ApplicationForHoliday() {
    }

    public ApplicationForHoliday(String id, String initiator, String recipient, String reason, int state, int variety) {
        this.id = id;
        this.initiator = initiator;
        this.recipient = recipient;
        this.reason = reason;
        this.state = state;
        this.variety = variety;
    }
}
