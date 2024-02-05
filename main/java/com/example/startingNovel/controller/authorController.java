package com.example.startingNovel.controller;

import com.example.startingNovel.pojo.author;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.authorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/author")
public class authorController {

    @Autowired
    private authorServiceImpl authorserviceimpl;

    @GetMapping("/setAuthor")
    public result<Boolean> setAuthor(@RequestParam String authorName){
        if(authorserviceimpl.setAuthor(authorName))
            return result.success(Boolean.TRUE);
        return result.fail(Boolean.FALSE);
    }

    @GetMapping("/getAuthorByAuthorId")
    public result<author> getAuthorByAuthorId(@RequestParam int authorId) {
        if(authorserviceimpl.getAuthorByAuthorId(authorId) == null)
            return result.fail(authorserviceimpl.getAuthorByAuthorId(authorId));
        return result.success(authorserviceimpl.getAuthorByAuthorId(authorId));
    }

    @GetMapping("/getAuthorByAuthorName")
    public result<author> getAuthorByAuthorName(@RequestParam String authorName) {
        if(authorserviceimpl.getAuthorByAuthorName(authorName) == null)
            return result.fail(authorserviceimpl.getAuthorByAuthorName(authorName));
        return result.success(authorserviceimpl.getAuthorByAuthorName(authorName));
    }

    @GetMapping("/list")
    public result<List<author>> getAllAuthor() {
        if(authorserviceimpl.getAllAuthor().isEmpty())
            return result.fail(authorserviceimpl.getAllAuthor());
        return result.success(authorserviceimpl.getAllAuthor());
    }
}
