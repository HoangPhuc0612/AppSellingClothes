package com.example.doanfashionapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doanfashionapp.DTO.Order_Detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO_Order_Details {
    private Context context;
    private SQLiteDatabase db;
    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";

    public DAO_Order_Details(Context context) {
        this.context = context;
    }

    public List<Order_Detail> getOrderDetailsByOrderId(String orderId) {
        List<Order_Detail> orderDetails = new ArrayList<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT * FROM ORDER_DETAIL WHERE ORDER_ID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{orderId});
        if (cursor.moveToFirst()) {
            int productIdIndex = cursor.getColumnIndex("PRODUCT_VARIATION_ID");
            int quantityIndex = cursor.getColumnIndex("QUANTITY");
            int subtotalIndex = cursor.getColumnIndex("SUBTOTAL");

            do {
                // Retrieve data from cursor
                String productId = cursor.getString(productIdIndex);
                int quantity = cursor.getInt(quantityIndex);
                int subtotal = cursor.getInt(subtotalIndex);

                // Create Order_Detail object and add to list
                orderDetails.add(new Order_Detail(orderId, productId, quantity, subtotal));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderDetails;
    }

    public void insertOrderDetail(Order_Detail orderDetail) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        ContentValues values = new ContentValues();
        values.put("ORDER_ID", orderDetail.getOrderId());
        values.put("PRODUCT_VARIATION_ID", orderDetail.getProductVariationId());
        values.put("QUANTITY", orderDetail.getQuantity());
        values.put("SUBTOTAL", orderDetail.getSubtotal());
        db.insert("ORDER_DETAIL", null, values);
        db.close();
    }

    public void updateProductVariationQuantity(String productVariationId, int newQuantity) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        ContentValues values = new ContentValues();
        values.put("QUANTITY", newQuantity);
        db.update("PRODUCT_VARIATIONS", values, "PRODUCT_VARIATION_ID = ?", new String[]{productVariationId});
        db.close();
    }

    public Map<String, String> getProductVariationDetails(String productVariationId) {
        Map<String, String> productDetails = new HashMap<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT SIZE, COLOR FROM PRODUCT_VARIATIONS WHERE PRODUCT_VARIATION_ID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{productVariationId});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("SIZE"));
            @SuppressLint("Range") String color = cursor.getString(cursor.getColumnIndex("COLOR"));
            productDetails.put("SIZE", size);
            productDetails.put("COLOR", color);
        }
        cursor.close();
        db.close();
        return productDetails;
    }

    // Get product ID from product variation ID
    public String getProductIdFromVariationId(String productVariationId) {
        String productId = null;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        Cursor cursor = db.rawQuery("SELECT PRODUCT_ID FROM PRODUCT_VARIATIONS WHERE PRODUCT_VARIATION_ID = ?", new String[]{productVariationId});
        if (cursor.moveToFirst()) {
            productId = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return productId;
    }

    // Get product image using product ID
    public int getProductImageByProductId(String productId) {
        int productImage = 0;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        Cursor cursor = db.rawQuery("SELECT ANH_DAI_DIEN FROM PRODUCTS WHERE PRODUCT_ID = ?", new String[]{productId});
        if (cursor.moveToFirst()) {
            String imageName = cursor.getString(0).replace(".jpg", "");
            productImage = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        }
        cursor.close();
        db.close();
        return productImage;
    }
}
