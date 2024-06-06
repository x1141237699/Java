package com.xiao.oasystem.exception;

public class NoSuchGroupException extends RuntimeException{
    public NoSuchGroupException() {
    }

    public NoSuchGroupException(String message) {
        super(message);
    }
}
