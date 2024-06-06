package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.result.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RecordService {

    public Result<List<Record>> list(int pageNum);
}
