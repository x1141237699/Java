package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.classification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface classificationMapper {

    classification getClassificationByClassificationName(@Param("classificationName") String classificationName);

    classification getClassificationByClassificationId(@Param("classificationId") int classificationId);

    List<classification> getAllClassification();
}
