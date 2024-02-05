package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.secondaryReviewMapper;
import com.example.startingNovel.pojo.secondaryReview;
import com.example.startingNovel.service.secondaryReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class secondaryReviewServiceImpl implements secondaryReviewService {

    @Autowired
    private secondaryReviewMapper secondaryreviewmapper;

    @Override
    public Boolean setSecondaryReview(int bookReviewId, String content) {
        secondaryreviewmapper.setSecondaryReview(bookReviewId,content);
        return Boolean.TRUE;
    }

    @Override
    public List<secondaryReview> getSecondaryReviewByBookReviewId(int bookReviewId) {
        return secondaryreviewmapper.getSecondaryReviewByBookReviewId(bookReviewId);
    }

    @Override
    public List<secondaryReview> getAllSecondaryReview() {
        return secondaryreviewmapper.getAllSecondaryReview();
    }
}
