package com.example.doanfashionapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.doanfashionapp.DTO.Favorite;
import com.example.doanfashionapp.DTO.SanPham;

import java.util.ArrayList;

public class DAO_SanPhamYeuThich {
    private static Context context;
    private static SQLiteDatabase db;
    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DAO_SanPhamYeuThich(Context context) {
        this.context = context;
    }


    private String getLoggedInUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", ""); // Trả về username hoặc chuỗi rỗng nếu không có giá trị
    }
    public void addToFavorites(String productId) {
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        ContentValues values = new ContentValues();
        String username= getLoggedInUsername();
        values.put("USERNAME", username);
        values.put("PRODUCT_ID", productId);
        db.insert("FAVORITES", null, values);
        db.close();
    }

    public void removeFromFavorites(String productId) {
        String username= getLoggedInUsername();
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        db.delete("FAVORITES", "USERNAME = ? AND PRODUCT_ID = ?", new String[]{username, productId});
        db.close();
    }

    public boolean isFavorite(String productID) {
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        String username = getLoggedInUsername();
        String query = "SELECT * FROM FAVORITES WHERE USERNAME = ? AND PRODUCT_ID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, productID});
        boolean isFavorite = cursor.moveToFirst();
        cursor.close();
        return isFavorite;
    }

    public ArrayList<String> getFavorites() {
        String username=getLoggedInUsername();
        ArrayList<String> favorites = new ArrayList<>();
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        Cursor cursor = db.query("FAVORITES", new String[]{"PRODUCT_ID"}, "USERNAME = ?", new String[]{username}, null, null, null);
        while (cursor.moveToNext()) {
            favorites.add(cursor.getString(cursor.getColumnIndexOrThrow("PRODUCT_ID")));
        }
        cursor.close();
        return favorites;
    }

    public ArrayList<Favorite> loadSPYeuThich() {
        String username=getLoggedInUsername();
        ArrayList<Favorite> arrayList = new ArrayList<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT PRODUCT_ID FROM FAVORITES WHERE USERNAME = '"+username+"'";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Favorite temp = new Favorite();
            temp.setMaSP(c.getString(0));
            ArrayList<SanPham> sanPhamList = new ArrayList<>();
            String dieuKien = c.getString(0);
            String sql2 = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = '" + dieuKien + "'";
            Cursor c2 = db.rawQuery(sql2, null);
            c2.moveToFirst();
            while (!c2.isAfterLast()) {
                String maSP = c2.getString(0);
                String tenSP = c2.getString(1);
                String loaiSP = c2.getString(2);
                int giaSP = c2.getInt(3);
                String bienTam = c2.getString(4);
                String[] parts = bienTam.split(".jpg");
                String anhDaiDien = parts[0];
                int idAnh = context.getResources().getIdentifier(anhDaiDien, "drawable", context.getPackageName());
                String moTa = c2.getString(5);
                String maHang = c2.getString(6);
                SanPham temp2 = new SanPham(maSP, idAnh, tenSP, giaSP, moTa, maHang, loaiSP);
                sanPhamList.add(temp2);
                c2.moveToNext();
            }
            temp.setArrayList(sanPhamList);
            if (!temp.getArrayList().isEmpty()) {
                arrayList.add(temp);
            }
            c.moveToNext();
            c2.close();
        }
        c.close();
        db.close();
        return arrayList;
    }
}
