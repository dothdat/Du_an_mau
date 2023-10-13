package com.example.du_an_mau.Daos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.du_an_mau.Database.DbHelper;

public class Thuthu_Dao {
    private DbHelper dbHelper;

    public Thuthu_Dao(Context context) {
        dbHelper =new DbHelper(context);

    }
    //login
    public boolean CheckLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap =? AND matkhau =?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

}
