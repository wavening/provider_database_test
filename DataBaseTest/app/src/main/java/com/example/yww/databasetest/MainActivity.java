package com.example.yww.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDataBaseHelper dbHelper;
    private Button btnCreate, btnAdd, btnUpdate, btnDelete, btnQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        dbHelper = new MyDataBaseHelper(this, "BookStore.db", null, 2);
        btnCreate = (Button) findViewById(R.id.create_database);
        btnAdd = (Button) findViewById(R.id.add_data);
        btnUpdate = (Button) findViewById(R.id.update_data);
        btnDelete = (Button) findViewById(R.id.delete_data);
        btnQuery = (Button) findViewById(R.id.query_data);
        btnCreate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_database:
                dbHelper.getWritableDatabase();
                break;
            case R.id.add_data:
                SQLiteDatabase db_add = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "The Da Vinvi Code");
                values.put("author", "Dan Brown");
                values.put("pages", "454");
                values.put("price", "16.96");
                db_add.insert("Book", null, values);
                values.clear();
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", "510");
                values.put("price", "19.95");
                db_add.insert("Book", null, values);
                break;
            case R.id.update_data:
                SQLiteDatabase db_update = dbHelper.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("price", 10.99);
                db_update.update("Book", values2, "name=?", new String[]{"The Da Vinci Code"});
                break;
            case R.id.delete_data:
                SQLiteDatabase db_delete = dbHelper.getWritableDatabase();
                db_delete.delete("Book", "pages > ?", new String[]{"500"});
                break;
            case R.id.query_data:
                SQLiteDatabase db_query = dbHelper.getWritableDatabase();
                Cursor cursor = db_query.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.e("yww", "book name is " + name);
                        Log.e("yww", "book author is " + author);
                        Log.e("yww", "book pages is " + pages);
                        Log.e("yww", "book price is " + price);
                    } while (cursor.moveToNext());
                }
                break;
        }
    }
}
