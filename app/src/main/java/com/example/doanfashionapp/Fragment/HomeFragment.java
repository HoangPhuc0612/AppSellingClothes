package com.example.doanfashionapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.doanfashionapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FrameLayout frameLayout;

    private ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;


    public HomeFragment(ActionBar actionBar1) {
        // Required empty public constructor
        actionBar=actionBar1;
    }
    public HomeFragment(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        frameLayout=(FrameLayout) view.findViewById(R.id.frameLayout);
        bottomNavigationView=(BottomNavigationView) view.findViewById(R.id.bottomNav);
        addEvents();
        return view;
    }

    private void addEvents(){
        loadFragment(new Home_Bottom_NavFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        actionBar.setTitle("Trang chủ");
                        loadFragment(new Home_Bottom_NavFragment());
                        return true;
                    case R.id.Catalog:
                        actionBar.setTitle("Bộ sưu tập");
                        loadFragment(new Catalog_Bottom_NavFragment());
                        return true;
                    case R.id.Profile:
                        actionBar.setTitle("");
                        loadFragment(new Profile_Bottom_NavFragment());
                        return true;
                }
                return false;
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.commit();
    }
}