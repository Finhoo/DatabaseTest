package com.fayne.android.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private Button btnCreateDatabase;
    private Button btnAddData;
    private Button btnUpdateData;
    private Button btnDeleteData;
    private Button btnQueryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateDatabase = findViewById(R.id.btnCreateDatabase);
        btnAddData = findViewById(R.id.btnAddData);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        btnDeleteData = findViewById(R.id.btnDeleteData);
        btnQueryData = findViewById(R.id.btnQueryData);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        btnCreateDatabase.setOnClickListener((view) -> {
            dbHelper.getWritableDatabase();
        });

        btnAddData.setOnClickListener((View view) -> {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);
                values.clear();

                //开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                //db.insert("Book", null, values);
                db.execSQL("insert into Book (name, author, pages, price) values (\"The Da Vinvi Code\", \"Dan Brown\", \"454\", \"19.96\")");
                db.execSQL("insert into Book (name, author, pages, price) values (\"FFF\", \"jinhao\", \"100\", \"10.00\")");
                values.clear();
        });

        btnUpdateData.setOnClickListener((view) -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("price", 66.66);
            db.update("Book", values, "name=?", new String[] {"The Da Vinci Code"});
            //db.execSQL("update Book set pages=7364 where name=\"jinhao\"");
        });

        btnDeleteData.setOnClickListener((view) -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("Book", "pages > ?", new String[] {"500"});
        });

        btnQueryData.setOnClickListener((view) -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //查询Book表中的所有数据
            //Cursor cursor = db.query("Book", null, null, null, null, null, null);
            Cursor cursor = db.rawQuery("select * from Book", null);
            if (cursor.moveToFirst()) {
                do {
                    // 遍历Cursor对象，取出数据并打印
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    Log.d("MainActivity", "book name is "+ name);
                    Log.d("MainActivity", "book author is "+ author);
                    Log.d("MainActivity", "book pages is "+ pages);
                    Log.d("MainActivity", "book price is "+ price);
                } while (cursor.moveToNext());
            }
            cursor.close();
        });
    }

}
