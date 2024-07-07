package com.example.doanfashionapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanfashionapp.DAO.DAO_Cart;
import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.CartItem;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SanPhamThanhToanGioHang_Adapter extends RecyclerView.Adapter<SanPhamThanhToanGioHang_Adapter.MyViewHolder> {
    private ArrayList<CartItem> cartItems;
    private Context context;
    private DAO_Cart daoCart;
    private int totalPrice = 0;
    private OnTotalPriceChangeListener onTotalPriceChangeListener;

    public SanPhamThanhToanGioHang_Adapter(ArrayList<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    public void setOnTotalPriceChangeListener(OnTotalPriceChangeListener listener) {
        this.onTotalPriceChangeListener = listener;
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (CartItem item : cartItems) {
            if (item.isSelected()) {
                totalPrice += item.getQuantity() * item.getSanPham().getGiaSanPham(); // Assuming getProduct returns the associated product
            }
        }
        if (onTotalPriceChangeListener != null) {
            onTotalPriceChangeListener.onTotalPriceChanged(totalPrice);
        }
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtGia, txtGiaTong, txtSL, txtSize, txtMau;
        CheckBox cbSP;
        public ImageView imgSP;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.imgSP);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtGiaTong = itemView.findViewById(R.id.txtGiaTong);
            txtSL = itemView.findViewById(R.id.txtSL);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtMau = itemView.findViewById(R.id.txtMau);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_sanphamthanhtoan_giohang, parent, false);
        daoCart = new DAO_Cart(parent.getContext());
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        SanPham sanPham = item.getSanPham();
        if (item!= null) {
            if(sanPham != null){
                holder.txtTitle.setText(sanPham.getTenSanPham());
                holder.txtGia.setText(String.valueOf(sanPham.getGiaSanPham()));
                holder.txtSL.setText(String.valueOf(item.getQuantity()));
                holder.imgSP.setImageResource(sanPham.getIdAnhSanPham());
                holder.txtMau.setText(item.getColor());
                holder.txtSize.setText(item.getSize());

                int price = sanPham.getGiaSanPham();
                int quantity = item.getQuantity();
                int totalPrice = price * quantity;
                holder.txtGiaTong.setText(String.valueOf(totalPrice));
            }
        }
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);
    }
}