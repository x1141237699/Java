package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.authorMapper;
import com.example.startingNovel.pojo.author;
import com.example.startingNovel.service.authorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class authorServiceImpl implements authorService {

    @Autowired
    private authorMapper authormapper;

    @Override
    public Boolean setAuthor(String authorName) {
        List<author> list = authormapper.getAllAuthor();
        for (author author : list) {
            if(author.getAuthorName().equals(authorName))
                return Boolean.FALSE;
        }
        authormapper.setAuthor(authorName);
        return Boolean.TRUE;
    }

    @Override
    public author getAuthorByAuthorId(int authorId) {
        return authormapper.getAuthorByAuthorId(authorId);
    }

    @Override
    public author getAuthorByAuthorName(String authorName) {
        return authormapper.getAuthorByAuthorName(authorName);
    }

    @Override
    public List<author> getAllAuthor() {
        return authormapper.getAllAuthor();
    }
}
