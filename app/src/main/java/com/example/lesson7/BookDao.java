package com.example.lesson7;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void InsertBook(Book book);
    @Query("SELECT * FROM Books")
    List<Book> GetBookList();
    @Query("SELECT * FROM Books WHERE Books.Id = :id")
    Book GetBookById(int id);
    @Query("DELETE FROM Books WHERE Books.Id = :id")
    void DeleteBook(int id);
}
