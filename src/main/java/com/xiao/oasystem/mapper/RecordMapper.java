package com.xiao.oasystem.mapper;

import com.xiao.oasystem.pojo.entity.Record;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {

    public void insertRecord(Record record);

    public List<Record> list();
}
