package com.example.doanfashionapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doanfashionapp.Adapter.ProductListAdapter;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.R;

import java.util.ArrayList;

public class DAO_SanPham {
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

    public DAO_SanPham(Context context) {
        this.context = context;
    }
    public ArrayList<SanPham> loadProducts(String brandId, String categoryId) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT * FROM PRODUCTS WHERE SEASON_ID = ? AND CATEGORY_ID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{brandId, categoryId});

        ArrayList<SanPham> productList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String maSP = cursor.getString(0);
            String tenSP = cursor.getString(1);
            String loaiSP = cursor.getString(2);
            int giaSP = cursor.getInt(3);
            String bienTam = cursor.getString(4);
            String[] parts = bienTam.split(".jpg");
            String anhDaiDien = parts[0];
            int idAnh = context.getResources().getIdentifier(anhDaiDien, "drawable", context.getPackageName());

            String moTa = cursor.getString(5);
            String maHang = cursor.getString(6);

            SanPham sanPham = new SanPham(maSP, idAnh, tenSP, giaSP, moTa, maHang, loaiSP);
            productList.add(sanPham);
        }
        cursor.close();
        db.close();
        return productList;
    }

}
