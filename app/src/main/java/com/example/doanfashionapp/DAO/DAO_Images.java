package com.example.doanfashionapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DAO_Images {
    private Context context;
    private SQLiteDatabase db;

    public DAO_Images(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase("FashionAppDB.db", Context.MODE_PRIVATE, null);
    }

    // Lấy danh sách hình ảnh từ bảng IMAGES dựa trên PRODUCT_ID
    public ArrayList<Integer> getImagesByProductId(String productId) {
        ArrayList<Integer> images = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT IMAGE_NAME FROM IMAGES WHERE PRODUCT_ID = ?", new String[]{productId});

        while (cursor.moveToNext()) {
            String bienTam = cursor.getString(0);
            String[] parts = bienTam.split(".jpg");
            String anhSP = parts[0];
            int idAnh = context.getResources().getIdentifier(anhSP, "drawable", context.getPackageName());
            if (idAnh != 0) { // Kiểm tra xem tài nguyên ảnh có tồn tại hay không
                images.add(idAnh);
            }
        }

        cursor.close();
        return images;
    }
    // Lấy hình ảnh đầu tiên từ bảng IMAGES dựa trên PRODUCT_ID
    public int getFirstImageByProductId(String productId) {
        int imageId = 0;
        Cursor cursor = db.rawQuery("SELECT IMAGE_NAME FROM IMAGES WHERE PRODUCT_ID = ? LIMIT 1", new String[]{productId});

        if (cursor.moveToFirst()) {
            String bienTam = cursor.getString(0);
            String[] parts = bienTam.split(".jpg");
            String anhSP = parts[0];
            imageId = context.getResources().getIdentifier(anhSP, "drawable", context.getPackageName());
        }

        cursor.close();
        return imageId;
    }
}
