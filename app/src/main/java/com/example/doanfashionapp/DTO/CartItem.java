package com.example.doanfashionapp.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CartItem implements Parcelable {
    private ArrayList<SanPham> arrayList;
    private int Cart_id;
    private String Username;
    private String product_variation_id;
    private int Quantity;
    private String size;
    private String color;
    private boolean isSelected;
    private SanPham sanPham;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public CartItem() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setArrayList(ArrayList<SanPham> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<SanPham> getArrayList() {
        return arrayList;
    }

    public CartItem(int cart_id, String username, String product_variation_id, int quantity, String size, String color) {
        Cart_id = cart_id;
        Username = username;
        this.product_variation_id = product_variation_id;
        Quantity = quantity;
        this.size = size;
        this.color = color;
    }

    public int getCart_id() {
        return Cart_id;
    }

    public void setCart_id(int cart_id) {
        Cart_id = cart_id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getProduct_variation_id() { // Changed method name
        return product_variation_id;
    }

    public void setProduct_variation_id(String product_variation_id) { // Changed method name
        this.product_variation_id = product_variation_id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    protected CartItem(Parcel in) {
        sanPham = in.readParcelable(SanPham.class.getClassLoader());
        Quantity = in.readInt();
        isSelected = in.readByte() != 0;
        product_variation_id = in.readString();
        color = in.readString();
        size = in.readString();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(sanPham, flags);
        dest.writeInt(Quantity);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeString(product_variation_id);
        dest.writeString(color);
        dest.writeString(size);
    }
}
