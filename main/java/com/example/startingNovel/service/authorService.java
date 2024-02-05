package com.example.startingNovel.service;

import com.example.startingNovel.pojo.author;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface authorService {

    Boolean setAuthor(String authorName);

    author getAuthorByAuthorId(int authorId);

    author getAuthorByAuthorName(String authorName);

    List<author> getAllAuthor();
}
