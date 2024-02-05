package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.author;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface authorMapper {


    void setAuthor(@Param("authorName") String authorName);

    author getAuthorByAuthorId(@Param("authorId") int authorId);

    author getAuthorByAuthorName(@Param("authorName") String authorName);

    List<author> getAllAuthor();
}
