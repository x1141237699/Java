package com.example.startingNovel.controller;

import com.example.startingNovel.exception.userAccountRepeatException;
import com.example.startingNovel.pojo.bookReview;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.bookReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/bookReview")
public class bookReviewController {

    @Autowired
    private bookReviewServiceImpl bookreviewserviceimpl;

    @GetMapping("/setBookReview")
    public result<Boolean> setBookReview(@RequestParam int bookId, @RequestParam String content){
        return result.success(bookreviewserviceimpl.setBookReview(bookId, content));
    }

    @GetMapping("/getBookReviewByBookId")
    public result<bookReview> getBookReviewByBookId(@RequestParam int bookId){
        if(bookreviewserviceimpl.getBookReviewByBookId(bookId) == null)
            return result.fail(bookreviewserviceimpl.getBookReviewByBookId(bookId));
        return result.success(bookreviewserviceimpl.getBookReviewByBookId(bookId));
    }

    @GetMapping("/list")
    public result<List<bookReview>> getAllBookReview(){
        if(bookreviewserviceimpl.getAllBookReview().isEmpty())
            return result.fail(bookreviewserviceimpl.getAllBookReview());
        return result.success(bookreviewserviceimpl.getAllBookReview());
    }
}
