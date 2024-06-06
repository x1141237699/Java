package com.xiao.oasystem.service.impl;

import com.xiao.oasystem.exception.ExceedPagingLimitException;
import com.xiao.oasystem.mapper.RecordMapper;
import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Result<List<Record>> list(int pageNum) {
        List<Record> result = new ArrayList<>();
        List<Record> records = recordMapper.list();
        records.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(result::add);
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }
}
