package com.example.startingNovel.controller;

import com.example.startingNovel.pojo.book;
import com.example.startingNovel.result.result;
import com.example.startingNovel.service.impl.bookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/book")
public class bookController {

    @Autowired
    private bookServiceImpl bookserviceimpl;

    @GetMapping("/startReading")
    public result<String> startReading(@RequestParam int userAccount, @RequestParam int bookId){
        return result.success(bookserviceimpl.startReading(userAccount, bookId));
    }

    @GetMapping("/setBook")
    public result<Boolean> setBook(@RequestParam int userAccount,@RequestParam String bookName,@RequestParam int bookPage,@RequestParam String authorName,@RequestParam String synopsis,@RequestParam String catalogue) {
        return result.success(bookserviceimpl.setBook(userAccount,bookName,bookPage,authorName,synopsis,catalogue));
    }

    @GetMapping("/setBookContent")
    public result<Boolean> setBookContent(@RequestParam int bookId,@RequestParam String content) {
        return result.success(bookserviceimpl.setBookContent(bookId,content));
    }

    @GetMapping("/updateBookContent")
    public result<Boolean> updateBookContent(@RequestParam int bookId,@RequestParam String content) {
        return result.success(bookserviceimpl.updateBookContent(bookId,content));
    }

    @GetMapping("/setBookLiked")
    public result<Boolean> setBookLiked(@RequestParam int userAccount,@RequestParam int bookId) {
        if(bookserviceimpl.setBookLiked(userAccount, bookId))
            return result.success(Boolean.TRUE);
        return result.fail(Boolean.FALSE);
    }

    @GetMapping("/setBookWanted")
    public result<Boolean> setBookWanted(@RequestParam int userAccount,@RequestParam int bookId) {
        if(bookserviceimpl.setBookWanted(userAccount, bookId))
            return result.success(Boolean.TRUE);
        return result.fail(Boolean.FALSE);
    }

    @GetMapping("/getLikedBookByUserAccount")
    public result<List<String>> getLikedBookByUserAccount(@RequestParam int userAccount) {
        if(bookserviceimpl.getLikedBookByUserAccount(userAccount).isEmpty())
            return result.fail(bookserviceimpl.getLikedBookByUserAccount(userAccount));
        return result.success(bookserviceimpl.getLikedBookByUserAccount(userAccount));
    }

    @GetMapping("/getWantedBookByUserAccount")
    public result<List<String>> getWantedBookByUserAccount(@RequestParam int userAccount) {
        if(bookserviceimpl.getWantedBookByUserAccount(userAccount).isEmpty())
            return result.fail(bookserviceimpl.getWantedBookByUserAccount(userAccount));
        return result.success(bookserviceimpl.getWantedBookByUserAccount(userAccount));
    }

    @GetMapping("/getPublishedBookByUserAccount")
    public result<List<String>> getPublishedBookByUserAccount(@RequestParam int userAccount) {
        if(bookserviceimpl.getPublishedBookByUserAccount(userAccount).isEmpty())
            return result.fail(bookserviceimpl.getPublishedBookByUserAccount(userAccount));
        return result.success(bookserviceimpl.getPublishedBookByUserAccount(userAccount));
    }

    @GetMapping("/getAllLikedBook")
    public result<List<String>> getAllLikedBook() {
        if(bookserviceimpl.getAllLikedBook().isEmpty())
            return result.fail(bookserviceimpl.getAllLikedBook());
        return result.success(bookserviceimpl.getAllLikedBook());
    }

    @GetMapping("/getAllWantedBook")
    public result<List<String>> getAllWantedBook() {
        if(bookserviceimpl.getAllWantedBook().isEmpty())
            return result.fail(bookserviceimpl.getAllWantedBook());
        return result.success(bookserviceimpl.getAllWantedBook());
    }

    @GetMapping("/getBookByBookName")
    public result<book> getBookByBookName(@RequestParam String bookName){
        if(bookserviceimpl.getBookByBookName(bookName) == null)
            return result.fail(bookserviceimpl.getBookByBookName(bookName));
        return result.success(bookserviceimpl.getBookByBookName(bookName));
    }

    @GetMapping("/getBookByBookId")
    public result<book> getBookByBookId(@RequestParam int bookId){
        if(bookserviceimpl.getBookByBookId(bookId) == null)
            return result.fail(bookserviceimpl.getBookByBookId(bookId));
        return result.success(bookserviceimpl.getBookByBookId(bookId));
    }

    @GetMapping("/getBookByAuthor")
    public result<List<book>> getBookByAuthor(@RequestParam String author){
        if(bookserviceimpl.getBookByAuthor(author).isEmpty())
            return result.fail(bookserviceimpl.getBookByAuthor(author));
        return result.success(bookserviceimpl.getBookByAuthor(author));
    }

    @GetMapping("/getBookByKeyword")
    public result<List<book>> getBookByKeyword(@RequestParam String keyword) {
        if(bookserviceimpl.getBookByKeyword(keyword).isEmpty())
            return result.fail(bookserviceimpl.getBookByKeyword(keyword));
        return result.success(bookserviceimpl.getBookByKeyword(keyword));
    }

    @GetMapping("/getBookByClassification")
    public result<List<book>> getBookByClassification(@RequestParam String classificationName) {
        if(bookserviceimpl.getBookByClassification(classificationName).isEmpty())
            return result.fail(bookserviceimpl.getBookByClassification(classificationName));
        return result.success(bookserviceimpl.getBookByClassification(classificationName));
    }

    @GetMapping("/list")
    public result<List<book>> getAllBook(){
        if(bookserviceimpl.getAllBook().isEmpty())
            return result.fail(bookserviceimpl.getAllBook());
        return result.success(bookserviceimpl.getAllBook());
    }
}
