package com.example.doanfashionapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.Favorite;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;
import com.example.doanfashionapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CustomFavoritesAdapter extends RecyclerView.Adapter<CustomFavoritesAdapter.MyViewHolder> {
    private ArrayList<Favorite> list;
    private SelectListener listener;
    private ArrayList<String> favoriteProductIds;
    private DAO_SanPhamYeuThich dao_sanPhamYeuThich;
    public CustomFavoritesAdapter(ArrayList<Favorite> list, SelectListener listener, ArrayList<String> favoriteProductIds) {
        this.list = list;
        this.listener = listener;
        this.favoriteProductIds = favoriteProductIds;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtGiaSP, txtTenSP;
        public ImageView imgSP;
        public ImageButton imgFavorite;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            imgSP = itemView.findViewById(R.id.imgAnhSP);
            imgFavorite = itemView.findViewById(R.id.imageFavorite);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_favorites_layout, parent, false);
        dao_sanPhamYeuThich=new DAO_SanPhamYeuThich(parent.getContext());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Favorite favorite = list.get(position);
        if(favorite != null){
            SanPham sanPham = favorite.getArrayList().get(0);
            holder.txtTenSP.setText(sanPham.getTenSanPham());
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getInstance(locale);
            holder.txtGiaSP.setText(format.format(sanPham.getGiaSanPham()) + " đ");
            holder.imgSP.setImageResource(sanPham.getIdAnhSanPham());

            //holder.imgFavorite.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
            if (dao_sanPhamYeuThich.isFavorite(sanPham.getIdSanPham())) {
                holder.imgFavorite.setImageResource(R.drawable.baseline_favorite_24); // Biểu tượng yêu thích
            } else {
                holder.imgFavorite.setImageResource(R.drawable.baseline_favorite_border_24); // Biểu tượng không yêu thích
            }


            // Xử lý sự kiện nhấp vào biểu tượng yêu thích
            holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isCurrentlyFavorite = dao_sanPhamYeuThich.isFavorite(sanPham.getIdSanPham());
                    listener.onItemFavoriteClicked(sanPham, isCurrentlyFavorite);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(sanPham);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateFavorites(ArrayList<String> newFavorites) {
        this.favoriteProductIds = newFavorites;
        notifyDataSetChanged();
    }
}