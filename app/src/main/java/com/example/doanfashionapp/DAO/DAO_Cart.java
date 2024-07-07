package com.example.doanfashionapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doanfashionapp.DTO.CartItem;
import com.example.doanfashionapp.DTO.SanPham;

import java.util.ArrayList;

public class DAO_Cart {
    private Context context;
    private SQLiteDatabase db;
    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";

    public DAO_Cart(Context context) {
        this.context = context;
    }

    public ArrayList<CartItem> getAllCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        String username = getLoggedInUsername();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT * FROM CART WHERE USERNAME = ?";
        Cursor c = db.rawQuery(sql, new String[]{username});
        if (c.moveToFirst()) {
            do {
                CartItem temp = new CartItem();
                temp.setCart_id(c.getInt(0));
                temp.setProduct_variation_id(c.getString(1));
                temp.setQuantity(c.getInt(2));
                cartItems.add(temp);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return cartItems;
    }

    public void addToCart(String productVariationId, int quantity) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String username = getLoggedInUsername();
        // Kiểm tra sản phẩm có tồn tại không
        String sql = "SELECT QUANTITY FROM CART WHERE USERNAME = ? AND PRODUCT_VARIATION_ID = ?";
        Cursor c = db.rawQuery(sql, new String[]{username, productVariationId});
        if (c != null && c.moveToFirst()) {
            // Nếu có tồn tại, cập nhật số lượng
            int currentQuantity = c.getInt(0);
            ContentValues values = new ContentValues();
            values.put("QUANTITY", currentQuantity + quantity);
            db.update("CART", values, "USERNAME = ? AND PRODUCT_VARIATION_ID = ?", new String[]{username, productVariationId});
        } else {
            // Sản phẩm không tồn tại, chèn mới
            ContentValues values = new ContentValues();
            values.put("USERNAME", username);
            values.put("PRODUCT_VARIATION_ID", productVariationId);
            values.put("QUANTITY", quantity);
            db.insert("CART", null, values);
        }
        if (c != null) {
            c.close();
        }
        db.close();
    }

    public void removeFromCart(String productVariationId) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        db.delete("CART", "PRODUCT_VARIATION_ID = ?", new String[]{productVariationId});
        db.close();
    }

    private String getLoggedInUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    public void updateQuantity(String productVariationId, int quantity) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        ContentValues values = new ContentValues();
        values.put("QUANTITY", quantity);
        db.update("CART", values, "PRODUCT_VARIATION_ID = ?", new String[]{productVariationId});
        db.close();
    }

    public ArrayList<CartItem> loadSPCart() {
        String username = getLoggedInUsername();
        ArrayList<CartItem> arrayList = new ArrayList<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT PRODUCT_VARIATION_ID, QUANTITY FROM CART WHERE USERNAME = ?";
        Cursor c = db.rawQuery(sql, new String[]{username});
        if (c.moveToFirst()) {
            do {
                CartItem temp = new CartItem();
                String productVariationId = c.getString(0);
                temp.setProduct_variation_id(productVariationId);
                temp.setQuantity(c.getInt(1));

                String sql2 = "SELECT SIZE, COLOR, PRODUCT_ID FROM PRODUCT_VARIATIONS WHERE PRODUCT_VARIATION_ID = ?";
                Cursor c2 = db.rawQuery(sql2, new String[]{productVariationId});
                if (c2.moveToFirst()) {
                    temp.setSize(c2.getString(0));
                    temp.setColor(c2.getString(1));
                    String productId = c2.getString(2);

                    String sql3 = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?";
                    Cursor c3 = db.rawQuery(sql3, new String[]{productId});
                    if (c3 != null && c3.moveToFirst()) {
                        String maSP = c3.getString(0);
                        String tenSP = c3.getString(1);
                        String loaiSP = c3.getString(2);
                        int giaSP = c3.getInt(3);
                        String bienTam = c3.getString(4);
                        String[] parts = bienTam.split(".jpg");
                        String anhDaiDien = parts[0];
                        int idAnh = context.getResources().getIdentifier(anhDaiDien, "drawable", context.getPackageName());
                        String moTa = c3.getString(5);
                        String maHang = c3.getString(6);
                        SanPham sanPham = new SanPham(maSP, idAnh, tenSP, giaSP, moTa, maHang, loaiSP);
                        temp.setSanPham(sanPham);
                    }
                    if (c3 != null) {
                        c3.close();
                    }
                }
                if (c2 != null) {
                    c2.close();
                }
                arrayList.add(temp);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return arrayList;
    }

    public void clearCart() {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        db.delete("CART", null, null);
        db.close();
    }
}
