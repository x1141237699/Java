package com.xiao.oasystem.pojo.entity;

import lombok.Data;

@Data
public class SocketMessage {
    private String sendOutUser;
    private String message;

    public SocketMessage() {
    }

    public SocketMessage(String sendOutUser, String message) {
        this.sendOutUser = sendOutUser;
        this.message = message;
    }
}
