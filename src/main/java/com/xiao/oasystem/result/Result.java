package com.xiao.oasystem.result;

import lombok.Data;

@Data
public class Result<T> {
    public static final int SUCCESS = 200;//成功
    public static final int CREATED = 201;//已创建
    public static final int ACCEPTED = 202;//已接受
    public static final int NO_CONTENT = 204;//无内容

    public static final int REQUEST_AIL = 400;//错误请求
    public static final int NOT_ACCREDITED = 401;//未授权
    public static final int FORBID = 403;//禁止
    public static final int NOT_FOUND = 404;//未找到

    public static final int SERVER_FAIL = 500;//服务器内部错误
    public static final int NOT_ACCOMPLISHED = 501;//尚未实施
    public static final int UNUSABLE_SERVER = 503;//服务不可用

    private int code;
    private String message;
    private T data;
    private long timestamp;

    public Result() {this.timestamp = System.currentTimeMillis();}

    public Result(int code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(int code, String message, T data){
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> fail(int code, String message, T data){
        return new Result<>(code, message, data);
    }
}
