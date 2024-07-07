package com.example.doanfashionapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class DAO_Account {
    private Context context;
    private static SQLiteDatabase db=null;
    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DAO_Account(Context context) {
        this.context = context;
    }

    public static void addToAccounts(String username, String email, String hoTen, String role, String sdt, String diaChi, String pass) {
        try {
            db=SQLiteDatabase.openOrCreateDatabase(path,null);
            ContentValues values = new ContentValues();
            values.put("USERNAME", username);
            values.put("EMAIL", email);
            values.put("HOTEN", hoTen);
            values.put("VAITRO", role);
            values.put("SO_DIEN_THOAI", sdt);
            values.put("DIA_CHI", diaChi);
            values.put("PASSWORD_HASH", pass);
            db.insert("ACCOUNTS", null, values);
            db.close();
        }
        catch (SQLiteConstraintException e){
            e.printStackTrace();
        }
    }
}
