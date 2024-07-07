package com.example.doanfashionapp.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class SanPham implements Parcelable {
    private String idSanPham;
    private int idAnhSanPham;
    private String tenSanPham;
    private int giaSanPham;
    private String moTaSP;
    private String idBrand;
    private String idLoaiSP;

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getIdAnhSanPham() {
        return idAnhSanPham;
    }

    public void setIdAnhSanPham(int idAnhSanPham) {
        this.idAnhSanPham = idAnhSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getMoTaSP() {
        return moTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        this.moTaSP = moTaSP;
    }

    public String getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(String idBrand) {
        this.idBrand = idBrand;
    }

    public String getIdLoaiSP() {
        return idLoaiSP;
    }

    public void setIdLoaiSP(String idLoaiSP) {
        this.idLoaiSP = idLoaiSP;
    }

    public SanPham() {

    }

    public SanPham(String idSanPham, int idAnhSanPham, String tenSanPham, int giaSanPham, String moTaSP, String idBrand, String idLoaiSP) {
        this.idSanPham = idSanPham;
        this.idAnhSanPham = idAnhSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.moTaSP = moTaSP;
        this.idBrand = idBrand;
        this.idLoaiSP = idLoaiSP;
    }
    protected SanPham(Parcel in) {
        idSanPham = in.readString();
        idAnhSanPham = in.readInt();
        tenSanPham = in.readString();
        giaSanPham = in.readInt();
        moTaSP = in.readString();
        idBrand = in.readString();
        idLoaiSP = in.readString();
    }

    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham(in);
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idSanPham);
        dest.writeInt(idAnhSanPham);
        dest.writeString(tenSanPham);
        dest.writeInt(giaSanPham);
        dest.writeString(moTaSP);
        dest.writeString(idBrand);
        dest.writeString(idLoaiSP);
    }
}
