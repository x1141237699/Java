package com.example.startingNovel.service.impl;

import com.example.startingNovel.mapper.bookMapper;
import com.example.startingNovel.mapper.historyMapper;
import com.example.startingNovel.pojo.book;
import com.example.startingNovel.pojo.history;
import com.example.startingNovel.service.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class bookServiceImpl implements bookService {

    @Autowired
    private bookMapper bookmapper;

    @Autowired
    private historyMapper historymapper;

    @Override
    public String startReading(int userAccount, int bookId) {
        List<history> list = historymapper.getAllHistory();
        for (history history : list) {
            if (history.getUserAccount() == userAccount && history.getBookId() == bookId) {
                historymapper.changeHistory(userAccount, bookId);
                return bookmapper.startReading(bookId);
            }
        }
        historymapper.setHistory(userAccount, bookId);
        return bookmapper.startReading(bookId);
    }

    @Override
    public Boolean setBook(int userAccount, String bookName, int bookPage, String authorName, String synopsis, String catalogue) {
        bookmapper.setBook(bookName,bookPage,authorName,synopsis,catalogue);
        book book = bookmapper.getBookByBookName(bookName);
        bookmapper.setBookPublished(userAccount,book.getBookId());
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateBookContent(int bookId, String content) {
        bookmapper.updateBookContent(bookId,content);
        return Boolean.TRUE;
    }

    @Override
    public Boolean setBookContent(int bookId, String content) {
        bookmapper.setBookContent(bookId,content);
        return Boolean.TRUE;
    }

    @Override
    public Boolean setBookLiked(int userAccount, int bookId) {
        List<Integer> list = bookmapper.getLikedBookIdByUserAccount(userAccount);
        for (Integer integer : list) {
            if (integer == bookId)
                return Boolean.FALSE;
        }
        bookmapper.setBookLiked(userAccount,bookId);
        return Boolean.TRUE;
    }

    @Override
    public Boolean setBookWanted(int userAccount, int bookId) {
        List<Integer> list = bookmapper.getWantedBookIdByUserAccount(userAccount);
        for (Integer integer : list) {
            if (integer == bookId)
                return Boolean.FALSE;
        }
        bookmapper.setBookWanted(userAccount,bookId);
        return Boolean.TRUE;
    }

    @Override
    public List<String> getLikedBookByUserAccount(int userAccount) {
        return bookmapper.getLikedBookByUserAccount(userAccount);
    }

    @Override
    public List<String> getWantedBookByUserAccount(int userAccount) {
        return bookmapper.getWantedBookByUserAccount(userAccount);
    }

    @Override
    public List<String> getPublishedBookByUserAccount(int userAccount) {
        return bookmapper.getPublishedBookByUserAccount(userAccount);
    }

    @Override
    public List<String> getAllLikedBook() {
        return bookmapper.getAllLikedBook();
    }

    @Override
    public List<String> getAllWantedBook() {
        return bookmapper.getAllWantedBook();
    }

    @Override
    public book getBookByBookName(String bookName) {
        return bookmapper.getBookByBookName(bookName);
    }

    @Override
    public book getBookByBookId(int bookId) {
        return bookmapper.getBookByBookId(bookId);
    }

    @Override
    public List<book> getBookByAuthor(String author) {
        return bookmapper.getBookByAuthor(author);
    }

    @Override
    public List<book> getBookByKeyword(String keyword) {
        return bookmapper.getBookByKeyword(keyword);
    }

    @Override
    public List<book> getBookByClassification(String classificationName) {
        return bookmapper.getBookByClassification(classificationName);
    }

    @Override
    public List<book> getAllBook() {
        return bookmapper.getAllBook();
    }
}
