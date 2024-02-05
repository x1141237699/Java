package com.example.startingNovel.result;

import lombok.Data;

@Data
public class result<T> {

    private int status;
    private String message;
    private T data;
    private long timestamp ;

    public result(){
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> result<T> success(T data){
        result<T> result = new result<>();
        result.setStatus(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> result<T> fail(T data){
        result<T> result = new result<>();
        result.setStatus(400);
        result.setMessage("数据异常");
        result.setData(data);
        return result;
    }

}
