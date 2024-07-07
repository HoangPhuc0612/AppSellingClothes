package com.example.doanfashionapp.Adapter;

import android.annotation.SuppressLint;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private ArrayList<CartItem> cartItems;
    private Context context;
    private DAO_Cart daoCart;
    private int totalPrice = 0;
    private OnTotalPriceChangeListener onTotalPriceChangeListener;

    public CartAdapter(ArrayList<CartItem> cartItems, Context context, DAO_Cart cart) {
        this.cartItems = cartItems;
        this.context = context;
        this.daoCart = cart;
    }

    public void setOnTotalPriceChangeListener(OnTotalPriceChangeListener listener) {
        this.onTotalPriceChangeListener = listener;
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (CartItem item : cartItems) {
            if (item.isSelected()) {
                totalPrice += item.getQuantity() * item.getSanPham().getGiaSanPham();
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
        TextView txtTitle, txtGia, txtGiaTong, txtPlus, txtSL, txtMinus, txtSize, txtColor;
        CheckBox cbSP;
        public ImageView imgSP;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.imgSP);
            cbSP = itemView.findViewById(R.id.cbSP);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtGiaTong = itemView.findViewById(R.id.txtGiaTong);
            txtPlus = itemView.findViewById(R.id.txtPlus);
            txtSL = itemView.findViewById(R.id.txtSL);
            txtMinus = itemView.findViewById(R.id.txtMinus);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtColor = itemView.findViewById(R.id.txtMau);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_cart, parent, false);
        daoCart = new DAO_Cart(parent.getContext());
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartItem item = cartItems.get(position);
        SanPham sanPham = item.getSanPham();
        if (item != null) {
            if (sanPham != null) {
                holder.txtTitle.setText(sanPham.getTenSanPham());
                holder.txtGia.setText(String.valueOf(sanPham.getGiaSanPham()));
                holder.txtSL.setText(String.valueOf(item.getQuantity()));
                holder.imgSP.setImageResource(sanPham.getIdAnhSanPham());
                holder.txtSize.setText(item.getSize());
                holder.txtColor.setText(item.getColor());
                int price = sanPham.getGiaSanPham();
                int quantity = item.getQuantity();
                int totalPrice = price * quantity;
                holder.txtGiaTong.setText(String.valueOf(totalPrice));
            }
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(item, position);
                return true;
            }
        });
        holder.cbSP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelected(isChecked);
                calculateTotalPrice();
            }
        });
        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = item.getQuantity() + 1;
                item.setQuantity(quantity);
                daoCart.updateQuantity(item.getProduct_variation_id(), quantity);
                holder.txtSL.setText(String.valueOf(quantity));
                holder.txtGiaTong.setText(String.valueOf(sanPham.getGiaSanPham() * quantity) + "đ");
                calculateTotalPrice();
            }
        });

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = item.getQuantity() - 1;
                if (quantity > 0) {
                    item.setQuantity(quantity);
                    daoCart.updateQuantity(item.getProduct_variation_id(), quantity);
                    holder.txtSL.setText(String.valueOf(quantity));
                    holder.txtGiaTong.setText(String.valueOf(sanPham.getGiaSanPham() * quantity) + "đ");
                    calculateTotalPrice();
                }
            }
        });
    }

    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(CartItem item, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void showDeleteDialog(final CartItem item, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xoá sản phẩm");
        builder.setMessage("Bạn có muốn xoá sản phẩm này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xoá sản phẩm khỏi danh sách và cập nhật lại RecyclerView
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());

                // Xoá sản phẩm khỏi cơ sở dữ liệu
                daoCart.removeFromCart(item.getProduct_variation_id());
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);
    }
}