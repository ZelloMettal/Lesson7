package com.example.lesson7;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Button button_add_book;
    Button button_get_all_book;
    Button button_get_book_by_id;
    Button button_delete_book;
    EditText edit_book_name;
    EditText edit_book_author;
    EditText edit_book_genre;
    EditText edit_book_id;
    EditText edit_book_description;
    TextView text_result_request;

    SharedPreferences SPref;
    DBContext DB;
    BookDao BookDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add_book = findViewById(R.id.ButtonAddBook);
        button_get_all_book = findViewById(R.id.ButtonGetAllBook);
        button_get_book_by_id = findViewById(R.id.ButtonGetBookById);
        button_delete_book = findViewById(R.id.ButtonDeleteBook);

        edit_book_id = findViewById(R.id.BookId);
        edit_book_name = findViewById(R.id.BookName);
        edit_book_author = findViewById(R.id.BookAuthor);
        edit_book_genre = findViewById(R.id.BookGenre);
        edit_book_description = findViewById(R.id.BookDescription);
        text_result_request = findViewById(R.id.ResultRequest);

        DB = DBContext.getInstance(this);
        BookDao = DB.BookDao();

        button_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = edit_book_name.getText().toString();
                String author = edit_book_author.getText().toString();
                String genre = edit_book_genre.getText().toString();
                String description = edit_book_description.getText().toString();
                if(name.isEmpty() && author.isEmpty() && genre.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Not All Fields Are Filled In", Toast.LENGTH_LONG).show();
                    return;
                }

                Executors.newSingleThreadExecutor().execute(()->{
                    BookDao.InsertBook(new Book(name, author, genre, description));
                });
                RefrashBookList();
                Toast.makeText(getApplicationContext(), "Book Added!", Toast.LENGTH_LONG).show();
            }
        });
        button_get_all_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefrashBookList();
            }
        });
        button_delete_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_book_id.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Id Specified", Toast.LENGTH_LONG).show();
                    return;
                }
                int id = Integer.parseInt(edit_book_id.getText().toString());

                Executors.newSingleThreadExecutor().execute(()->{
                    Book book = BookDao.GetBookById(id);
                    if(book != null)
                    {
                        BookDao.DeleteBook(book.Id);
                        runOnUiThread(()->RefrashBookList());
                        runOnUiThread(()->Toast.makeText(getApplicationContext(), "Book Deleted!", Toast.LENGTH_LONG).show());
                    }
                    else
                    {
                        runOnUiThread(()->Toast.makeText(getApplicationContext(), "ID not found", Toast.LENGTH_LONG).show());
                    }
                });
            }
        });

        button_get_book_by_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_book_id.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Id Specified", Toast.LENGTH_LONG).show();
                    return;
                }
                int id = Integer.parseInt(edit_book_id.getText().toString());

                Executors.newSingleThreadExecutor().execute(()->{
                    Book book = BookDao.GetBookById(id);
                    if(book != null)
                        runOnUiThread(()->text_result_request.setText(
                                "ID= "+book.Id+
                                "\nName = "+book.BookName+
                                "\nAuthor = "+book.AuthorName+
                                "\nGenre = "+book.Genre+
                                "\nDescription = "+book.Description
                        ));
                    else
                        runOnUiThread(()->Toast.makeText(getApplicationContext(), "ID not found", Toast.LENGTH_LONG).show());
                });
            }
        });
    }
    private void RefrashBookList()
    {
        text_result_request.setText("");
        Executors.newSingleThreadExecutor().execute(()->{
            List<Book> books = BookDao.GetBookList();
            for(Book book:books)
                runOnUiThread(()->text_result_request.append(
                                "ID= "+book.Id+
                                ", Name = "+book.BookName+
                                ", Author = "+book.AuthorName+
                                ", Genre = "+book.Genre+"\n"
                ));
        });
    }
}