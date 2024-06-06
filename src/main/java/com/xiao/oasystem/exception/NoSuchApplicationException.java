package com.xiao.oasystem.exception;

public class NoSuchApplicationException extends RuntimeException{
    public NoSuchApplicationException() {
    }

    public NoSuchApplicationException(String message) {
        super(message);
    }
}
