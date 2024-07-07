package com.example.doanfashionapp.DTO;

import java.util.ArrayList;

public class Favorite {
    private String user;
    private String maSP;
    private ArrayList<SanPham> arrayList;

    public ArrayList<SanPham> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<SanPham> arrayList) {
        this.arrayList = arrayList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public Favorite() {
    }

    public Favorite(String user, String maSP, ArrayList<SanPham> arrayList) {
        this.user = user;
        this.maSP = maSP;
        this.arrayList = arrayList;
    }
}
