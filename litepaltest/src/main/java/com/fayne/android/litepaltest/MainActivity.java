package com.fayne.android.litepaltest;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreateDatabase = findViewById(R.id.btnCreateDatabase);
        btnCreateDatabase.setOnClickListener(view -> LitePal.getDatabase());

        findViewById(R.id.btnAddData).setOnClickListener((View view) -> {
            Book book = new Book();
            book.setName("The Da Vinci Code");
            book.setAuthor("Dan Brown");
            book.setPages(453);
            book.setPress("Unknow");
            book.setPrice(16.96);
            book.save();
        });

        findViewById(R.id.btnUpdateData).setOnClickListener((View view) -> {
           /* Book book = new Book();
            book.setName("The Lost Symbol");
            book.setAuthor("Dan Brown");
            book.setPages(510);
            book.setPress("Unknow");
            book.setPrice(19.95);
            book.save();
            book.setPrice(10.99);
            book.save();*/

           Book book = new Book();
           book.setPrice(14.95);
           book.setPress("Anchor");
           book.updateAll("name = ? and author= ?", "The Lost Symbol", "Dan Brown");
           book.setToDefault("pages");
           book.updateAll();
        });

        findViewById(R.id.btnDeleteData).setOnClickListener(view -> DataSupport.deleteAll(Book.class, "price < ?", "15"));

        findViewById(R.id.btnQueryData).setOnClickListener(view -> {
//            List<Book> books = DataSupport.findAll(Book.class);
//            Book firstBook = DataSupport.findFirst(Book.class);
//            Book lastBook = DataSupport.findLast(Book.class);
//            List<Book> books = DataSupport
//                    .select("name ", "author", "pages")
//                    .where("pages > ?", "400")
//                    .order("pages")
//                    .limit(10)
//                    .offset(10)
//                    .find(Book.class);
//            for (Book book : books) {
//                Log.d("MainActivity", "book name is " + book.getName());
//                Log.d("MainActivity", "book author is " + book.getAuthor());
//                Log.d("MainActivity", "book pages is " + book.getPages());
////                Log.d("MainActivity", "book price is " + book.getPrice());
////                Log.d("MainActivity", "book press is " + book.getPress());
//            }
            Cursor cursor = DataSupport.findBySQL("select * from Book where pages > ? and price < ?", "400", "20");
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    Log.d("MainActivity", "Book name is "+ name);
                }while (cursor.moveToNext());
            }
            cursor.close();
        });


    }
}
