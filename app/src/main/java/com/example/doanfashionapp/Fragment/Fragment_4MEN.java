package com.example.doanfashionapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.doanfashionapp.Adapter.CustomGridViewAdapter;
import com.example.doanfashionapp.DAO.DAO_LoaiSanPham;
import com.example.doanfashionapp.DTO.LoaiSanPham;
import com.example.doanfashionapp.ProductListForCategoryActivity;
import com.example.doanfashionapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_4MEN#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_4MEN extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GridView gridView;
    private CustomGridViewAdapter adapter;
    private ArrayList<LoaiSanPham> arrayList=new ArrayList<>();
    public Fragment_4MEN() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_4MEN.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_4MEN newInstance(String param1, String param2) {
        Fragment_4MEN fragment = new Fragment_4MEN();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_4men, container, false);
        gridView=(GridView) view.findViewById(R.id.gridView_4Men);
        DAO_LoaiSanPham dao_loaiSanPham=new DAO_LoaiSanPham(getContext());
        arrayList=dao_loaiSanPham.loadLoaiSP("Mùa Đông");
        adapter=new CustomGridViewAdapter(arrayList,getContext());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoaiSanPham selectedCategory = arrayList.get(position);
                Intent intent = new Intent(getActivity(), ProductListForCategoryActivity.class);
                intent.putExtra("BRAND_ID", "BR001");
                intent.putExtra("CATEGORY_ID", selectedCategory.getIdLoaiSP());
                startActivity(intent);
            }
        });
        return view;
    }
}