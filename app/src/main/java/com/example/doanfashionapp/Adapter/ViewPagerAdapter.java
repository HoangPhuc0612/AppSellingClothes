package com.example.doanfashionapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doanfashionapp.Fragment.Fragment_4MEN;
import com.example.doanfashionapp.Fragment.Fragment_Highway;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_4MEN();
            case 1:
                return new Fragment_Highway();
            default:
                return new Fragment_4MEN();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Mùa Đông";
                break;
            case 1:
                title="Mùa Xuân";
                break;
            default:
                title = "Mùa Đông";
                break;
        }
        return title;
    }
}
