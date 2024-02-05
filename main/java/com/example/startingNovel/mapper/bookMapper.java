package com.example.startingNovel.mapper;

import com.example.startingNovel.pojo.book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;

@Mapper
public interface bookMapper {

    String startReading(@Param("bookId") int bookId);

    void setBook(@Param("bookName") String bookName,@Param("bookPage") int bookPage,@Param("authorName") String authorName,@Param("synopsis") String synopsis,@Param("catalogue") String catalogue);

    void setBookContent(@Param("bookId") int bookId,@Param("content") String content);

    void updateBookContent(@Param("bookId") int bookId,@Param("content") String content);

    void setBookLiked(@Param("userAccount") int userAccount, @Param("bookId") int bookId);

    void setBookWanted(@Param("userAccount") int userAccount, @Param("bookId") int bookId);

    void setBookPublished(@Param("userAccount") int userAccount,@Param("bookId") int bookId);

    List<String> getLikedBookByUserAccount(@Param("userAccount") int userAccount);

    List<String> getWantedBookByUserAccount(@Param("userAccount") int userAccount);

    List<String> getPublishedBookByUserAccount(@Param("userAccount") int userAccount);

    List<Integer> getLikedBookIdByUserAccount(@Param("userAccount") int userAccount);

    List<Integer> getWantedBookIdByUserAccount(@Param("userAccount") int userAccount);

    List<String> getAllLikedBook();

    List<String> getAllWantedBook();

    book getBookByBookName(@Param("bookName") String bookName);

    book getBookByBookId(@Param("bookId") int bookId);

    List<book> getBookByAuthor(@Param("authorName") String authorName);

    List<book> getBookByKeyword(@Param("keyword") String keyword);

    List<book> getBookByClassification(@Param("classificationName") String classificationName);

    List<book> getAllBook();
}
