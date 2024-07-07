package com.example.doanfashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanfashionapp.Adapter.CustomGridViewAdapter_Search_Results;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SelectListener {
    private GridView gridView;
    private ArrayList<SanPham> searchResults=new ArrayList<>();
    private CustomGridViewAdapter_Search_Results adapter;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        gridView = findViewById(R.id.gridViewSearchResults);
        searchResults = getIntent().getParcelableArrayListExtra("searchResults"); // nhận tham số intent searchResults để trả về ds sản phẩm

        adapter = new CustomGridViewAdapter_Search_Results(this, R.layout.custom_gridview_item_search_result, searchResults,this);
        gridView.setAdapter(adapter);

        backButton = (ImageButton) findViewById(R.id.action_back);
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

