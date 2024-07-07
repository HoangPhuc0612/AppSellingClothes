package com.example.doanfashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanfashionapp.Adapter.ProductListAdapter;
import com.example.doanfashionapp.DAO.DAO_HangSanPham;
import com.example.doanfashionapp.DAO.DAO_LoaiSanPham;
import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;

import java.util.ArrayList;

public class ProductsList_For_Brands_Activity extends AppCompatActivity implements SelectListener {
    private GridView gridView;
    private ProductListAdapter adapter;
    private ArrayList<SanPham> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list_for_brands);

        gridView = findViewById(R.id.gridViewProductListSeeAll);

        // Nhận dữ liệu từ Intent
        String categoryId = getIntent().getStringExtra("CATEGORY_ID");

        // Lấy danh sách sản phẩm theo Brand ID
        DAO_LoaiSanPham dao_loaiSanPham = new DAO_LoaiSanPham(this);
        productList = dao_loaiSanPham.getProductsByCategoryId(categoryId);

        // Set adapter cho GridView
        adapter = new ProductListAdapter(this, R.layout.custom_item_product, productList,this);
        gridView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.action_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView txtTenMaBrand = findViewById(R.id.txtTenMaBrand);
        String tenMaBrand = getIntent().getStringExtra("CATEGORY_NAME");
        txtTenMaBrand.setText(tenMaBrand);
    }

    @Override
    public void onItemClicked(SanPham sp) {
        Intent intent = new Intent(getApplicationContext(), ActivityThongTinSanPham.class);
        intent.putExtra("tenSP",sp.getTenSanPham());
        intent.putExtra("giaSP",sp.getGiaSanPham());
        intent.putExtra("loaiSP",sp.getIdLoaiSP());
        intent.putExtra("motaSP",sp.getMoTaSP());
        intent.putExtra("brandSP",sp.getIdBrand());
        intent.putExtra("anhSP",sp.getIdAnhSanPham());
        startActivity(intent);
    }

    @Override
    public void onItemFavoriteClicked(SanPham sp, boolean isFavorite) {

    }
}