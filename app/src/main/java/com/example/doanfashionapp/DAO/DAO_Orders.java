package com.example.doanfashionapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doanfashionapp.DTO.Order;
import com.example.doanfashionapp.DTO.Order_Detail;

import java.util.ArrayList;
import java.util.List;

public class DAO_Orders {
    private Context context;
    private SQLiteDatabase db = null;
    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";

    public DAO_Orders(Context context) {
        this.context = context;
    }

    public List<Order> loadAllOrders() {
        List<Order> orders = new ArrayList<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT * FROM ORDERS";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int orderIdIndex = cursor.getColumnIndex("ORDER_ID");
                int usernameIndex = cursor.getColumnIndex("USERNAME");
                int orderDateIndex = cursor.getColumnIndex("ORDER_DATE");
                int totalPriceIndex = cursor.getColumnIndex("TOTAL_PRICE");

                if (orderIdIndex != -1 && usernameIndex != -1 && orderDateIndex != -1 && totalPriceIndex != -1) {
                    String orderId = cursor.getString(orderIdIndex);
                    String username = cursor.getString(usernameIndex);
                    String orderDate = cursor.getString(orderDateIndex);
                    int totalPrice = cursor.getInt(totalPriceIndex);

                    orders.add(new Order(orderId, username, orderDate, totalPrice));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orders;
    }

    public List<Order_Detail> loadOrderDetails(String orderId) {
        List<Order_Detail> orderDetails = new ArrayList<>();
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "SELECT * FROM ORDER_DETAIL WHERE ORDER_ID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{orderId});
        if (cursor.moveToFirst()) {
            int orderDetailIdIndex = cursor.getColumnIndex("ORDER_DETAIL_ID");
            int productIdIndex = cursor.getColumnIndex("PRODUCT_ID");
            int quantityIndex = cursor.getColumnIndex("QUANTITY");
            int subtotalIndex = cursor.getColumnIndex("SUBTOTAL");

            if (orderDetailIdIndex != -1 && productIdIndex != -1 && quantityIndex != -1 && subtotalIndex != -1) {
                do {
                    String orderDetailId = cursor.getString(orderDetailIdIndex);
                    String productId = cursor.getString(productIdIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    int subtotal = cursor.getInt(subtotalIndex);

                    orderDetails.add(new Order_Detail(orderId, productId, quantity, subtotal));
                } while (cursor.moveToNext());
            } else {
                // Log an error or handle the situation where column index is -1
            }
        }

        cursor.close();
        db.close();
        return orderDetails;
    }
    public void insertOrder(Order order) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        ContentValues values = new ContentValues();
        values.put("ORDER_ID", order.getOrderId());
        values.put("USERNAME", order.getUsername());
        values.put("ORDER_DATE", order.getOrderDate());
        values.put("TOTAL_PRICE", order.getTotalPrice());
        db.insert("ORDERS", null, values);
        db.close();
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
}
