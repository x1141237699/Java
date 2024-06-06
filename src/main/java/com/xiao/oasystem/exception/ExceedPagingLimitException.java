package com.xiao.oasystem.exception;

public class ExceedPagingLimitException extends RuntimeException{
    public ExceedPagingLimitException() {
    }

    public ExceedPagingLimitException(String message) {
        super(message);
    }
}
