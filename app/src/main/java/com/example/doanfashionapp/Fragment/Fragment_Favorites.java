package com.example.doanfashionapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanfashionapp.ActivityThongTinSanPham;
import com.example.doanfashionapp.Adapter.CustomFavoritesAdapter;
import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.Favorite;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;
import com.example.doanfashionapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_4MEN#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Favorites extends Fragment implements SelectListener {
    RecyclerView recyclerView;
    CustomFavoritesAdapter adapter;
    ArrayList<Favorite> arrayList;

    DAO_SanPhamYeuThich dao_sanPhamYeuThich;
    private ArrayList<String> favoriteProductIds;
    public Fragment_Favorites(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        dao_sanPhamYeuThich=new DAO_SanPhamYeuThich(getContext());
        favoriteProductIds = dao_sanPhamYeuThich.getFavorites();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.recycler_Favorite);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        arrayList = dao_sanPhamYeuThich.loadSPYeuThich();

        adapter = new CustomFavoritesAdapter(arrayList, Fragment_Favorites.this, favoriteProductIds);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onItemClicked(SanPham sp) {
        Intent intent = new Intent(getContext(), ActivityThongTinSanPham.class);
        intent.putExtra("maSP", sp.getIdSanPham());
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
        if (isFavorite) {
            dao_sanPhamYeuThich.removeFromFavorites(sp.getIdSanPham());
            Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            dao_sanPhamYeuThich.addToFavorites(sp.getIdSanPham());
            Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
        }
        favoriteProductIds = dao_sanPhamYeuThich.getFavorites();
        adapter.updateFavorites(favoriteProductIds);
    }
}