package com.example.startingNovel.controller;

import com.example.startingNovel.pojo.secondaryReview;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.secondaryReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/secondaryReview")
public class secondaryReviewController {

    @Autowired
    private secondaryReviewServiceImpl secondaryreviewserviceimpl;

    @GetMapping("/setSecondaryReview")
    public result<Boolean> setSecondaryReview(@RequestParam int bookReviewId, @RequestParam String content) {
        return result.success(secondaryreviewserviceimpl.setSecondaryReview(bookReviewId,content));
    }

    @GetMapping("/getSecondaryReviewByBookReviewId")
    public result<List<secondaryReview>> getSecondaryReviewByBookReviewId(@RequestParam int bookReviewId) {
        if(secondaryreviewserviceimpl.getSecondaryReviewByBookReviewId(bookReviewId).isEmpty())
            return result.fail(secondaryreviewserviceimpl.getSecondaryReviewByBookReviewId(bookReviewId));
        return result.success(secondaryreviewserviceimpl.getSecondaryReviewByBookReviewId(bookReviewId));
    }

    @GetMapping("/list")
    public result<List<secondaryReview>> getAllSecondaryReview() {
        if(secondaryreviewserviceimpl.getAllSecondaryReview().isEmpty())
            return result.fail(secondaryreviewserviceimpl.getAllSecondaryReview());
        return result.success(secondaryreviewserviceimpl.getAllSecondaryReview());
    }
}
