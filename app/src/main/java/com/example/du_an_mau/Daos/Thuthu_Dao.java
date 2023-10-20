package com.example.du_an_mau.Daos;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.du_an_mau.Database.DbHelper;
import com.example.du_an_mau.model.ThuThu;

import java.util.ArrayList;

public class Thuthu_Dao {
    private final DbHelper dbHelper;
    public Thuthu_Dao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThuThu> selectAll() {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM NGUOIDUNG", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ThuThu thuThu = new ThuThu();
                    thuThu.setMaTT(cursor.getString(0));
                    thuThu.setTenTT(cursor.getString(1));
                    thuThu.setMatKhau(cursor.getString(2));
                    thuThu.setChucVu(cursor.getInt(3));
                    list.add(thuThu);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
    public boolean insert(ThuThu tt) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", tt.getMaTT());
        values.put("tenTT", tt.getTenTT());
        values.put("matKhau", tt.getMatKhau());
        values.put("chucVu", tt.getChucVu());
        long row = db.insert("NGUOIDUNG", null, values);
        return (row > 0);
    }
    public boolean update(ThuThu tt) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", tt.getMaTT());
        values.put("tenTT", tt.getTenTT());
        values.put("matKhau", tt.getMatKhau());
        values.put("chucVu", tt.getChucVu());
        long row = db.update("NGUOIDUNG", values, "maTT = ?", new String[]{String.valueOf(tt.getMaTT())});
        return (row > 0);
    }
    public boolean checklogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NGUOIDUNG WHERE maTT=? and matKhau=?", new String[] {username, password});
        if (cursor.getCount() != 0)
            return true;
        else
            return false;
    }
    public boolean checkMaTT(String maTT) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maTT FROM NGUOIDUNG WHERE maTT = ?", new String[] {maTT});
        if (cursor.getCount() != 0)
            return true;
        else
            return false;
    }
    public boolean doiMatKhau(String maTT, String matKhau) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matKhau", matKhau);
        long row = db.update("NGUOIDUNG", values, "maTT = ?", new String[]{String.valueOf(maTT)});
        return (row > 0);
    }

}
