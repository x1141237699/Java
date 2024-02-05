package com.example.startingNovel.controller;

import com.example.startingNovel.pojo.keyword;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.keywordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/keyword")
public class keywordController {

    @Autowired
    private keywordServiceImpl keywordserviceimpl;

    @GetMapping("/getKeywordByKeywordId")
    public result<keyword> getKeywordByKeywordId(@RequestParam int keywordId){
        if(keywordserviceimpl.getKeywordByKeywordId(keywordId) == null)
            return result.fail(keywordserviceimpl.getKeywordByKeywordId(keywordId));
        return result.success(keywordserviceimpl.getKeywordByKeywordId(keywordId));
    }

    @GetMapping("/getKeywordByContent")
    public result<keyword> getKeywordByContent(@RequestParam String content){
        if(keywordserviceimpl.getKeywordByContent(content) == null)
            return result.fail(keywordserviceimpl.getKeywordByContent(content));
        return result.success(keywordserviceimpl.getKeywordByContent(content));
    }

    @GetMapping("/list")
    public result<List<keyword>> getAllKeyword(){
        if(keywordserviceimpl.getAllKeyword().isEmpty())
            return result.fail(keywordserviceimpl.getAllKeyword());
        return result.success(keywordserviceimpl.getAllKeyword());
    }
}
