package com.example.doanfashionapp.Interface;

import com.example.doanfashionapp.DTO.SanPham;

public interface SelectListener {
    void onItemClicked(SanPham sp);
    void onItemFavoriteClicked(SanPham sp,boolean isFavorite);
}
