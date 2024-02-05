package com.example.startingNovel.exception;

public class userAccountRepeatException extends RuntimeException{
    public userAccountRepeatException() {
    }

    public userAccountRepeatException(String message) {
        super(message);
    }
}
