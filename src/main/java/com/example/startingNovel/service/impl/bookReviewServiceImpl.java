package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.bookReviewMapper;
import com.example.startingNovel.pojo.bookReview;
import com.example.startingNovel.service.bookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class bookReviewServiceImpl implements bookReviewService {

    @Autowired
    private bookReviewMapper bookreviewmapper;

    @Override
    public Boolean setBookReview(int bookId, String content) {
        bookreviewmapper.setBookReview(bookId,content);
        return Boolean.TRUE;
    }

    @Override
    public bookReview getBookReviewByBookId(int bookId) {
        return bookreviewmapper.getBookReviewByBookId(bookId);
    }

    @Override
    public List<bookReview> getAllBookReview() {
        return bookreviewmapper.getAllBookReview();
    }
}
