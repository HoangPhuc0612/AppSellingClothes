package com.example.doanfashionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;
import com.example.doanfashionapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CustomGridViewAdapter_Search_Results extends ArrayAdapter<SanPham> {
    private Context context;
    private int resource;
    private ArrayList<SanPham> objects;
    private SelectListener listener;

    private DAO_SanPhamYeuThich dao_sanPhamYeuThich;

    public CustomGridViewAdapter_Search_Results(Context context, int resource, ArrayList<SanPham> objects,SelectListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.listener=listener;
        this.dao_sanPhamYeuThich=new DAO_SanPhamYeuThich(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        SanPham sanPham = objects.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageSPSearch);
        TextView textViewName = convertView.findViewById(R.id.txtTenSPSearch);
        TextView textViewPrice = convertView.findViewById(R.id.txtGiaSPSearch);
        ImageButton imageFavorite = (ImageButton) convertView.findViewById(R.id.imageFavoriteSearch);

        imageView.setImageResource(sanPham.getIdAnhSanPham());
        textViewName.setText(sanPham.getTenSanPham());
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        textViewPrice.setText(format.format(sanPham.getGiaSanPham()) + " đ");
        boolean isFavorite = dao_sanPhamYeuThich.isFavorite(sanPham.getIdSanPham());
        imageFavorite.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);

        imageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavoriteStatus(sanPham, imageFavorite);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(sanPham);
            }
        });

        return convertView;
    }

    private void toggleFavoriteStatus(SanPham sanPham, ImageButton imageFavorite) {

        if (dao_sanPhamYeuThich.isFavorite(sanPham.getIdSanPham())) {
            dao_sanPhamYeuThich.removeFromFavorites(sanPham.getIdSanPham());
            imageFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
            Toast.makeText(context, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            dao_sanPhamYeuThich.addToFavorites(sanPham.getIdSanPham());
            imageFavorite.setImageResource(R.drawable.baseline_favorite_24);
            Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
        }
    }
}
