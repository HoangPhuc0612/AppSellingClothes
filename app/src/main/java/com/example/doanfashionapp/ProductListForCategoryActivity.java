package com.example.doanfashionapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanfashionapp.Adapter.ProductListAdapter;
import com.example.doanfashionapp.DAO.DAO_SanPham;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;

import java.util.ArrayList;

public class ProductListForCategoryActivity extends AppCompatActivity implements SelectListener {

    private GridView gridView;
    private ProductListAdapter adapter;
    private ArrayList<SanPham> productList;

    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_for_category);

        gridView = findViewById(R.id.gridViewProductListCatalog);

        Intent intent = getIntent();
        String brandId = intent.getStringExtra("BRAND_ID");
        String categoryId = intent.getStringExtra("CATEGORY_ID");
        if (brandId != null && categoryId != null) {
            DAO_SanPham dao_sanPham=new DAO_SanPham(getApplicationContext());
            productList=dao_sanPham.loadProducts(brandId, categoryId);
            adapter = new ProductListAdapter(this, R.layout.custom_item_product, productList,this);
            gridView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Lỗi thông tin", Toast.LENGTH_SHORT).show();
        }

        ImageButton backButton = (ImageButton) findViewById(R.id.action_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
