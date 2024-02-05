package com.example.startingNovel.controller;

import com.example.startingNovel.pojo.rankingList;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.rankingListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/rankingList")
public class rankingListController {

    @Autowired
    private rankingListServiceImpl rankinglistserviceimpl;

    @GetMapping("/newList")
    public result<List<rankingList>> getNewList(){
        if(rankinglistserviceimpl.getNewList().isEmpty())
            return result.fail(rankinglistserviceimpl.getNewList());
        return result.success(rankinglistserviceimpl.getNewList());
    }

    @GetMapping("/hotList")
    public result<List<rankingList>> getHotList() {
        if(rankinglistserviceimpl.getHotList().isEmpty())
            return result.fail(rankinglistserviceimpl.getHotList());
        return result.success(rankinglistserviceimpl.getHotList());
    }
}
