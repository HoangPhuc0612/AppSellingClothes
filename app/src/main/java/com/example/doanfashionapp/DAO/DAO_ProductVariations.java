package com.example.doanfashionapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DAO_ProductVariations {
    private Context context;
    private SQLiteDatabase db;
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
    public DAO_ProductVariations(Context context){
        this.context = context;
    }

    public List<String> getSizesByProductId(String productId) {
        List<String> sizes = new ArrayList<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        Cursor cursor = db.rawQuery("SELECT DISTINCT SIZE FROM PRODUCT_VARIATIONS WHERE PRODUCT_ID = ?", new String[]{productId});
        if (cursor.moveToFirst()) {
            do {
                sizes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sizes;
    }

    public List<String> getColorsByProductId(String productId) {
        List<String> colors = new ArrayList<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        Cursor cursor = db.rawQuery("SELECT DISTINCT COLOR FROM PRODUCT_VARIATIONS WHERE PRODUCT_ID = ?", new String[]{productId});
        if (cursor.moveToFirst()) {
            do {
                colors.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return colors;
    }

    public String getProductVariationId(String productId, String selectedSize, String selectedColor) {
        String productVariationId = null;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        Cursor cursor = db.rawQuery("SELECT PRODUCT_VARIATION_ID FROM PRODUCT_VARIATIONS WHERE PRODUCT_ID = ? AND SIZE = ? AND COLOR = ?", new String[]{productId, selectedSize, selectedColor});
        if (cursor.moveToFirst()) {
            productVariationId = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return productVariationId;
    }

    public int getProductVariationQuantity(String productVariationId) {
        db = SQLiteDatabase.openOrCreateDatabase(DAO_Order_Details.path, null);
        String sql = "SELECT QUANTITY FROM PRODUCT_VARIATIONS WHERE PRODUCT_VARIATION_ID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{productVariationId});
        int quantity = 0;
        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return quantity;
    }
}
