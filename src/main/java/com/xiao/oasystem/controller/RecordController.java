package com.xiao.oasystem.controller;

import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/list")
    public Result<List<Record>> setRecord(@RequestParam(defaultValue = "1") int pageNum){
        return recordService.list(pageNum);
    }

}
