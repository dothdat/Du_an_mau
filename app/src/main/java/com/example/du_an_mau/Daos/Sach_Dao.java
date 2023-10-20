package com.example.du_an_mau.Daos;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.du_an_mau.Database.DbHelper;
import com.example.du_an_mau.model.Sach;
import com.example.du_an_mau.model.loaiSach;
import com.example.du_an_mau.model.thanhVien;

import java.util.ArrayList;

public class Sach_Dao {
    private DbHelper db_Sach;

    public Sach_Dao(Context context) {
        db_Sach = new DbHelper(context);
    }
    public ArrayList<Sach> getDSSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase database = db_Sach.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM tb_Sach", null);
            if (cursor.getCount() > 0) {
                //neu cursor lon hon 0 di chuyen con tro len dau
                cursor.moveToFirst();
                do {
                    list.add(new Sach(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getInt(5)));
                } while (cursor.moveToNext());
            }while (cursor.moveToNext()) ;
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }finally {
            database.endTransaction();
        }

        return list;
    }
    //Thêm sách
    public boolean insert(String tenSach,String tacGia,int giaThue, int loaiSach, int namXB) {
        SQLiteDatabase db = db_Sach.getWritableDatabase(); //getWrite.. ghi dữ liệu vào database
        ContentValues value = new ContentValues(); // đưa dữ lệu vào database
        value.put("tenSach", tenSach);
        value.put("tacGia", tacGia);
        value.put("giaThue", giaThue);
        value.put("maLoai", loaiSach);
        value.put("namXB", namXB);
        long check = db.insert("tb_Sach", null, value);
        return check != -1;
    }
    //Sửa sách
    public boolean suaSach(Sach sach){
        SQLiteDatabase database = db_Sach.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("tenSach", sach.getTenSach());
        value.put("tacGia", sach.getTacGia());
        value.put("giaThue", sach.getGiaThue());
        value.put("namXB", sach.getNamXB());
        int check = database.update("tb_Sach", value, "maSach = ?",
                new String[]{String.valueOf(sach.getMaSach())});
        return check != 0;
    }
    //Xóa sách
    public boolean delete (int maSach){
        SQLiteDatabase database = db_Sach.getWritableDatabase();
        int check = database.delete("tb_Sach", "maSach = ?",
                new String[]{String.valueOf(maSach)});
        if (check <=0) return false;
        return true;
    }
    String tenSach;
    public String getTenS(int maSach) {
        SQLiteDatabase db = db_Sach.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT tenSach FROM tb_Sach WHERE tb_Sach.maSach = ?", new String[] {String.valueOf(maSach)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    tenSach = cursor.getString(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return tenSach;
    }
    int tienThue;
    public int getTienThue(String tenS) {
        SQLiteDatabase db = db_Sach.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT giaThue FROM tb_Sach WHERE tb_Sach.tenSach = ?", new String[] {tenS});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    tienThue = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return tienThue;
    }
    int row;
    public int getMaS(String tenS) {
        SQLiteDatabase db = db_Sach.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT maSach FROM tb_Sach WHERE tb_Sach.tenSach = ?", new String[] {tenS});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    row = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return row;
    }
}
