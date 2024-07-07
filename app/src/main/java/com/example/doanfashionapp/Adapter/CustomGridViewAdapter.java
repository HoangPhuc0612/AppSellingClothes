package com.example.doanfashionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanfashionapp.DTO.LoaiSanPham;
import com.example.doanfashionapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class CustomGridViewAdapter extends BaseAdapter {
    private ArrayList<LoaiSanPham> arrayList;
    private Context context;
    private LayoutInflater layoutInflater;

    public ArrayList<LoaiSanPham> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<LoaiSanPham> arrayList) {
        this.arrayList = arrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public CustomGridViewAdapter(ArrayList<LoaiSanPham> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.custom_gridview_layout,null);
            viewHolder=new ViewHolder();
            //viewHolder.imageLoaiSP=(ImageView)convertView.findViewById(R.id.imageLoaiSP);
            viewHolder.roundedImageView=(RoundedImageView) convertView.findViewById(R.id.imageLoaiSP);
            viewHolder.txtTenLoaiSP=(TextView) convertView.findViewById(R.id.txtTenLoaiSP);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiSanPham loaiSP=arrayList.get(position);
        if (!loaiSP.getDsSP().isEmpty()){
            viewHolder.roundedImageView.setImageResource(loaiSP.getDsSP().get(0).getIdAnhSanPham());
            //viewHolder.imageLoaiSP.setImageResource(loaiSP.getDsSP().get(0).getIdAnhSanPham());
            viewHolder.txtTenLoaiSP.setText(loaiSP.getTenLoaiSP());
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imageLoaiSP;
        TextView txtTenLoaiSP;

        RoundedImageView roundedImageView;
    }
}
