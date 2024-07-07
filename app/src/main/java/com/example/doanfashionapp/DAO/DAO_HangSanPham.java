package com.example.doanfashionapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doanfashionapp.DTO.HangSanPham;
import com.example.doanfashionapp.DTO.SanPham;

import java.util.ArrayList;

public class DAO_HangSanPham {
    private Context context;
    private SQLiteDatabase db=null;
    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public DAO_HangSanPham(Context context) {
        this.context = context;
    }

    public String getTenHangSanPham(String brandId) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String tenHangSP = null;
        try {
            String sql = "SELECT SEASON_NAME FROM SEASONS WHERE SEASON_ID = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{brandId});
            if (cursor != null && cursor.moveToFirst()) {
                tenHangSP = cursor.getString(0);
                cursor.close();
            }
        } finally {
            db.close();
        }
        return tenHangSP;
    }
}
