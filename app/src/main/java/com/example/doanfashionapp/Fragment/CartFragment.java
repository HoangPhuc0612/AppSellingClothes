package com.example.doanfashionapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanfashionapp.ActivityXuLiThanhToan;
import com.example.doanfashionapp.Activity_XuLyThanhToanGioHang;
import com.example.doanfashionapp.Adapter.CartAdapter;
import com.example.doanfashionapp.DAO.DAO_Cart;
import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.CartItem;
import com.example.doanfashionapp.R;

import java.util.ArrayList;

public class CartFragment extends Fragment implements CartAdapter.OnTotalPriceChangeListener {
    private static final int SHIPPING_FEE = 30000;
    DAO_Cart daoCart;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;
    private ImageButton backButton;
    private TextView txtSoTien, txtTongTien, emptyTxt;
    private Button btnThanhToan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartItems = new ArrayList<>();
        daoCart = new DAO_Cart(getContext());
//        favoriteProductIds = dao_sanPhamYeuThich.getFavorites();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        backButton = (ImageButton) view.findViewById(R.id.action_back);
        txtSoTien = view.findViewById(R.id.txtSoTien);
        txtTongTien = view.findViewById(R.id.txtTongTien);
        emptyTxt = view.findViewById(R.id.emptyTxt);
        btnThanhToan = view.findViewById(R.id.btnThanhToan);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        recyclerView = view.findViewById(R.id.rc);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        cartItems = daoCart.loadSPCart();

        if (cartItems.isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
        } else {
            emptyTxt.setVisibility(View.GONE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartAdapter = new CartAdapter(cartItems, requireContext(), daoCart);
        cartAdapter.setOnTotalPriceChangeListener(this);
        recyclerView.setAdapter(cartAdapter);

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhToan();
            }
        });

        updateTotalPriceDisplay(0);

        return view;
    }

    @Override
    public void onTotalPriceChanged(int totalPrice) {
        updateTotalPriceDisplay(totalPrice);
    }

    private void updateTotalPriceDisplay(int totalPrice) {
        txtSoTien.setText(totalPrice + " VNĐ");
        if(totalPrice != 0){
            int tongTien = totalPrice + SHIPPING_FEE;
            txtTongTien.setText(tongTien + " VNĐ");
        }
        else {
            txtTongTien.setText(totalPrice + "VNĐ");
        }
    }

    private void thanhToan() {
        if (cartItems.isEmpty()) {
            Toast.makeText(getContext(), "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<CartItem> selectedItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }

        if (selectedItems.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng chọn sản phẩm để thanh toán!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển tới ActivityXuLyThanhToanGioHang khi nhấn vào button "Thanh toán"
        Intent intent = new Intent(getActivity(), Activity_XuLyThanhToanGioHang.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("selectedItems", selectedItems); // selectedItems là danh sách các mục được chọn trong giỏ hàng
        intent.putExtras(bundle);
        startActivity(intent);
    }
}