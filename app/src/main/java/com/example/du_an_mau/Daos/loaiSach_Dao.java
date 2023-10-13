package com.example.du_an_mau.Daos;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.du_an_mau.Database.DbHelper;
import com.example.du_an_mau.model.loaiSach;

import java.util.ArrayList;

public class loaiSach_Dao {
    private DbHelper db_loaiSach;

    public loaiSach_Dao(Context context) {
        db_loaiSach = new DbHelper(context);
    }

    public ArrayList<loaiSach> getDSLoaiSach() {
        ArrayList<loaiSach> list = new ArrayList<>();

        SQLiteDatabase database = db_loaiSach.getReadableDatabase();

        //database bat day chay
        database.beginTransaction();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM tb_LoaiSach", null);
            if (cursor.getCount() > 0) {
                //neu cursor lon hon 0 di chuyen con tro len dau
                cursor.moveToFirst();
                do {
                    list.add(new loaiSach(cursor.getInt(0),
                            cursor.getString(1)));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        }catch (Exception ex){
            Log.e("TAG",ex.getMessage());
        }finally {
            database.endTransaction();
        }
        return list;
    }
        //sửa loại sách

        public boolean suaLoaiSach(loaiSach loaiSach){
            SQLiteDatabase database = db_loaiSach.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("tenLoai", loaiSach.getTenLoai());
            int check = database.update("tb_LoaiSach", values, "maLoai=?",
                    new String[]{String.valueOf(loaiSach.getMaLoai())});
            return check != 0;
        }
         //thêm loại sách
        public boolean insert (String tenLoai){
            SQLiteDatabase db = db_loaiSach.getWritableDatabase(); //getWrite.. ghi dữ liệu vào database
            ContentValues value = new ContentValues(); // đưa dữ lệu vào database
            value.put("tenLoai", tenLoai);
            //nếu add thành công thì trả về giá trị là số dòng được chèn vào bảng
            long row = db.insert("tb_LoaiSach", null, value);
            return row != -1;
        }

        // xóa loại sách
        public boolean delete (int maLoai){
            SQLiteDatabase database = db_loaiSach.getWritableDatabase();
            int check = database.delete("tb_LoaiSach", "maLoai=?",
                    new String[]{String.valueOf(maLoai)});
           if (check <=0) return false;
            return true;
        }
    String row;
    public String getTenLoai(int maLoai) {
        SQLiteDatabase db = db_loaiSach.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT tenLoai FROM tb_LoaiSach WHERE tb_LoaiSach.maLoai = ?", new String[] {String.valueOf(maLoai)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    row = cursor.getString(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return row;
    }
    int raw;
    public int getMaLoai(String tenLoai) {
        SQLiteDatabase db = db_loaiSach.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT maLoai FROM tb_LoaiSach WHERE tb_LoaiSach.tenLoai = ?", new String[] {tenLoai});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    raw = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return raw;
    }
    }
