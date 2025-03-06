package com.example.lesson7;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    int Id;
    String BookName;
    String AuthorName;
    String Genre;
    String Description;

    public Book(){};
    public Book(String book_name, String author_name, String genre, String description)
    {
        this.BookName = book_name;
        this.AuthorName = author_name;
        this.Genre = genre;
        this.Description = description;
    }
}
