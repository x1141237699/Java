package com.xiao.oasystem.exception;

public class NoSuchDepartmentException extends RuntimeException{
    public NoSuchDepartmentException() {
    }

    public NoSuchDepartmentException(String message) {
        super(message);
    }
}
