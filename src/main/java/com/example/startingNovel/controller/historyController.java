package com.example.startingNovel.controller;

import com.example.startingNovel.pojo.history;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.historyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/history")
public class historyController {

    @Autowired
    private historyServiceImpl historyserviceimpl;

    @GetMapping("/getHistoryByUserAccount")
    public result<List<history>> getHistoryByUserAccount(@RequestParam int userAccount) {
        if(historyserviceimpl.getHistoryByUserAccount(userAccount).isEmpty())
            return result.fail(historyserviceimpl.getHistoryByUserAccount(userAccount));
        return result.success(historyserviceimpl.getHistoryByUserAccount(userAccount));
    }

    @GetMapping("/setHistory")
    public result<Boolean> setHistory(@RequestParam int userAccount,@RequestParam int bookId){
        historyserviceimpl.setHistory(userAccount, bookId);
        return result.success(Boolean.TRUE);
    }

    @GetMapping("/changeHistory")
    public result<Boolean> changeHistory(@RequestParam int userAccount,@RequestParam int bookId){
        historyserviceimpl.changeHistory(userAccount, bookId);
        return result.success(Boolean.TRUE);
    }

    @GetMapping("/list")
    public result<List<history>> getAllHistory() {
        if(historyserviceimpl.getAllHistory().isEmpty())
            return result.fail(historyserviceimpl.getAllHistory());
        return result.success(historyserviceimpl.getAllHistory());
    }
}
