package com.xiao.oasystem.pojo.BO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class StateUpdateBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    private String id;
    private int state;

    public StateUpdateBO() {
    }

    public StateUpdateBO(String id, int state) {
        this.id = id;
        this.state = state;
    }
}
