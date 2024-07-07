package com.example.doanfashionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;
import com.example.doanfashionapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends ArrayAdapter<SanPham> {

    private Context context;
    private int resource;
    private List<SanPham> productList;

    private SelectListener listener;

    private DAO_SanPhamYeuThich dao_sanPhamYeuThich;

    public ProductListAdapter(Context context, int resource, List<SanPham> productList,SelectListener listener) {
        super(context, resource, productList);
        this.context = context;
        this.resource = resource;
        this.productList = productList;
        this.listener=listener;
        this.dao_sanPhamYeuThich=new DAO_SanPhamYeuThich(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        SanPham product = productList.get(position);

        RoundedImageView imageView = convertView.findViewById(R.id.imageSPCategory);
        TextView textViewName = convertView.findViewById(R.id.txtTenSPCategory);
        TextView textViewPrice = convertView.findViewById(R.id.txtGiaSPCategory);
        ImageButton imageFavorite = convertView.findViewById(R.id.imageFavoriteCategory);

        imageView.setImageResource(product.getIdAnhSanPham());
        textViewName.setText(product.getTenSanPham());
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        textViewPrice.setText(format.format(product.getGiaSanPham()) + " đ");
        boolean isFavorite = dao_sanPhamYeuThich.isFavorite(product.getIdSanPham());
        imageFavorite.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);

        imageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavorite = dao_sanPhamYeuThich.isFavorite(product.getIdSanPham());
                if (isFavorite) {
                    dao_sanPhamYeuThich.removeFromFavorites(product.getIdSanPham());
                    imageFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
                    Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    dao_sanPhamYeuThich.addToFavorites(product.getIdSanPham());
                    imageFavorite.setImageResource(R.drawable.baseline_favorite_24);
                    Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(product);
            }
        });

        return convertView;
    }

}
