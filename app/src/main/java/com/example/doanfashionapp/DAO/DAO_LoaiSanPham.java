package com.example.doanfashionapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.doanfashionapp.DTO.LoaiSanPham;
import com.example.doanfashionapp.DTO.SanPham;



import java.util.ArrayList;

public class DAO_LoaiSanPham {
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

    public DAO_LoaiSanPham(Context context) {
        this.context = context;
    }

    // Load 2 fragment season
    public ArrayList<LoaiSanPham> loadLoaiSP(String brand){
        ArrayList<LoaiSanPham> arrayList=new ArrayList<>();
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        String sql="Select * from CATEGORIES";
        Cursor c =db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            LoaiSanPham temp = new LoaiSanPham();
            temp.setIdLoaiSP(c.getString(0));
            temp.setTenLoaiSP(c.getString(1));
            temp.setDsSP(new ArrayList<>());
            String dieuKien=c.getString(0);
            String sql2="Select * from PRODUCTS where CATEGORY_ID = '"+dieuKien+"' and SEASON_ID in (Select SEASON_ID from SEASONS where SEASON_NAME = '"+brand+"')";
            Cursor c2=db.rawQuery(sql2,null);
            c2.moveToFirst();
            while (!c2.isAfterLast()){

                String maSP=c2.getString(0);
                String tenSP=c2.getString(1);
                String loaiSP=c2.getString(2);
                int giaSP=c2.getInt(3);
                String bienTam=c2.getString(4);
                String parts[]=bienTam.split(".jpg");
                String anhDaiDien=parts[0];
                int idAnh=context.getResources().getIdentifier(anhDaiDien,"drawable",context.getPackageName());
                String moTa=c2.getString(5);
                String maHang=c2.getString(6);
                SanPham temp2= new SanPham(maSP,idAnh,tenSP,giaSP,moTa,maHang,loaiSP);
                temp.getDsSP().add(temp2);
                c2.moveToNext();
            }
            if (!temp.getDsSP().isEmpty()){
                arrayList.add(temp);
            }
            c.moveToNext();
            c2.close();
        }
        c.close();
        db.close();
        return arrayList;
    }
    public String getTenLoaiSanPham(String categoryId) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String tenLoaiSP = null;
        try {
            String sql = "SELECT CATEGORY_NAME FROM CATEGORIES WHERE CATEGORY_ID = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{categoryId});
            if (cursor != null && cursor.moveToFirst()) {
                tenLoaiSP = cursor.getString(0);
                cursor.close();
            }
        } finally {
            db.close();
        }
        return tenLoaiSP;
    }

    // load view sản phẩm ở trang chủ theo từng loại sản phẩm
    public ArrayList<LoaiSanPham> loadLoaiSP(){
        ArrayList<LoaiSanPham> arrayList=new ArrayList<>();
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        String sql="Select * from CATEGORIES";
        Cursor c =db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            LoaiSanPham temp = new LoaiSanPham();
            temp.setIdLoaiSP(c.getString(0));
            temp.setTenLoaiSP(c.getString(1));
            temp.setDsSP(new ArrayList<>());
            String dieuKien=c.getString(0);
            String sql2="Select * from PRODUCTS where CATEGORY_ID = '"+dieuKien+"'";
            Cursor c2=db.rawQuery(sql2,null);
            c2.moveToFirst();
            while (!c2.isAfterLast()){
                String maSP=c2.getString(0);
                String tenSP=c2.getString(1);
                String loaiSP=c2.getString(2);
                int giaSP=c2.getInt(3);
                String bienTam=c2.getString(4);
                String parts[]=bienTam.split(".jpg");
                String anhDaiDien=parts[0];
                int idAnh=context.getResources().getIdentifier(anhDaiDien,"drawable",context.getPackageName());
                String moTa=c2.getString(5);
                String maHang=c2.getString(6);
                SanPham temp2= new SanPham(maSP,idAnh,tenSP,giaSP,moTa,maHang,loaiSP);
                temp.getDsSP().add(temp2);
                c2.moveToNext();
            }
            if (!temp.getDsSP().isEmpty()){
                arrayList.add(temp);
            }
            c.moveToNext();
            c2.close();
        }
        c.close();
        db.close();
        return arrayList;
    }
    public ArrayList<SanPham> getProductsByCategoryId(String loadSPId){
        ArrayList<SanPham> arrayList=new ArrayList<>();
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        String sql="Select * from PRODUCTS WHERE CATEGORY_ID = '" + loadSPId + "'";
        Cursor c =db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maSP = c.getString(0);
            String tenSP = c.getString(1);
            String loaiSP = c.getString(2);
            int giaSP = c.getInt(3);
            String bienTam = c.getString(4);
            String parts[] = bienTam.split(".jpg");
            String anhDaiDien = parts[0];
            int idAnh = context.getResources().getIdentifier(anhDaiDien, "drawable", context.getPackageName());
            String moTa = c.getString(5);
            String maHang = c.getString(6);
            SanPham temp2 = new SanPham(maSP, idAnh, tenSP, giaSP, moTa, maHang, loaiSP);
            arrayList.add(temp2);
            c.moveToNext();
        }
        c.close();
        db.close();
        return arrayList;
    }
}
