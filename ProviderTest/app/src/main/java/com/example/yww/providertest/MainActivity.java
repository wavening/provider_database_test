package com.example.yww.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String newId;
    private Button add, query, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        add = (Button) findViewById(R.id.add_data);
        query = (Button) findViewById(R.id.query_data);
        update = (Button) findViewById(R.id.update_data);
        delete = (Button) findViewById(R.id.delete_data);
        add.setOnClickListener(this);
        query.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_data:
                Uri uri_add = Uri.parse("content://com.example.yww.databasetest.provider/book");
                ContentValues values_add = new ContentValues();
                values_add.put("name", "A clash of Kings");
                values_add.put("author", "George Martin");
                values_add.put("pages", "1040");
                values_add.put("price", "22.85");
                Uri newUri = getContentResolver().insert(uri_add, values_add);
                newId = newUri.getPathSegments().get(1);
                break;
            case R.id.query_data:
                Uri uri_query = Uri.parse("content://com.example.yww.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri_query, null, null, null, null);
                if (null != cursor) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.e("yww", "book name is " + name);
                        Log.e("yww", "book author is " + author);
                        Log.e("yww", "book pages is " + pages);
                        Log.e("yww", "book price is " + price);
                    }
                }
                break;
            case R.id.update_data:
                Uri uri_update = Uri.parse("content://com.example.yww.databasetest.provider/book/" + newId);
                ContentValues values_update = new ContentValues();
                values_update.put("name", "A Storm of Swords");

                values_update.put("pages", "1216");
                values_update.put("price", "24.05");
                getContentResolver().update(uri_update, values_update, null, null);
                break;
            case R.id.delete_data:
                Uri uri_delete = Uri.parse("content://com.example.yww.databasetest.provider/book/" + newId);
                getContentResolver().delete(uri_delete, null, null);
                break;
            default:

                break;
        }
    }
}
