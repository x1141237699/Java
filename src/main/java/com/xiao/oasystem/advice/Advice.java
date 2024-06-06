package com.xiao.oasystem.advice;

import com.xiao.oasystem.exception.*;
import com.xiao.oasystem.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = Controller.class)
public class Advice {

    @ExceptionHandler(NoSuchApplicationException.class)
    public Result<String> doNoSuchApplicationException(NoSuchApplicationException e){
        log.error("不存在用户" + e.getMessage());
        return Result.fail(Result.SERVER_FAIL,"不存在该申请","id: " + e.getMessage());
    }

    @ExceptionHandler(NoSuchDepartmentException.class)
    public Result<String> doNoSuchDepartmentException(NoSuchDepartmentException e){
        log.error("不存在部门" + e.getMessage());
        return Result.fail(Result.SERVER_FAIL,"不存在该部门","id: " + e.getMessage());
    }

    @ExceptionHandler(NoSuchGroupException.class)
    public Result<String> doNoSuchGroupException(NoSuchGroupException e){
        log.error("不存在工作组" + e.getMessage());
        return Result.fail(Result.SERVER_FAIL,"不存在该工作组","id: " + e.getMessage());
    }

    @ExceptionHandler(NoSuchUserException.class)
    public Result<String> doNoSuchUserException(NoSuchUserException e){
        log.error("不存在用户" + e.getMessage());
        return Result.fail(Result.SERVER_FAIL,"不存在该用户","id: " + e.getMessage());
    }

    @ExceptionHandler(ExceedPagingLimitException.class)
    public Result<String> doExceedPagingLimitException(ExceedPagingLimitException e){
        log.error(e.getMessage());
        return Result.fail(Result.SERVER_FAIL, e.getMessage(), null);
    }
}
