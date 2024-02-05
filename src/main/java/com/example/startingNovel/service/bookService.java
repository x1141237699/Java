package com.example.startingNovel.service;

import com.example.startingNovel.pojo.book;

import java.util.List;

public interface bookService {

    String startReading(int userAccount, int bookId);

    Boolean setBook(int userAccount, String bookName, int bookPage, String authorName, String synopsis, String catalogue);

    Boolean setBookContent(int bookId, String content);

    Boolean updateBookContent(int bookId, String content);

    Boolean setBookLiked(int userAccount, int bookId);

    Boolean setBookWanted(int userAccount, int bookId);

    List<String> getLikedBookByUserAccount(int userAccount);

    List<String> getWantedBookByUserAccount(int userAccount);

    List<String> getPublishedBookByUserAccount(int userAccount);

    List<String> getAllLikedBook();

    List<String> getAllWantedBook();

    book getBookByBookName(String bookName);

    book getBookByBookId(int bookId);

    List<book> getBookByAuthor(String author);

    List<book> getBookByKeyword(String keyword);

    List<book> getBookByClassification(String classificationName);

    List<book> getAllBook();
}
