package com.example.doanfashionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;
import com.example.doanfashionapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CustomRecyclerViewAdapter_Home_Bottom_Nav extends RecyclerView.Adapter<CustomRecyclerViewAdapter_Home_Bottom_Nav.ViewHolder> {
    private ArrayList<SanPham> listSP;
    private Context context;
    private SelectListener listener;
    private DAO_SanPhamYeuThich dao_sanPhamYeuThich;

    public CustomRecyclerViewAdapter_Home_Bottom_Nav(ArrayList<SanPham> listSP, Context context,SelectListener listener1) {
        this.listSP = listSP;
        this.context = context;
        this.listener=listener1;
        this.dao_sanPhamYeuThich=new DAO_SanPhamYeuThich(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.custom_recyclerview_home_bottom_nav,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sp = listSP.get(position);
        if (sp!=null){
            holder.txtTenSP.setText(sp.getTenSanPham());
            Locale locale =new Locale("vi","VN");
            NumberFormat format=NumberFormat.getInstance(locale);
            holder.txtGiaSP.setText(format.format(sp.getGiaSanPham())+" đ");
            //holder.imageSP.setImageResource(sp.getIdAnhSanPham());
            holder.roundedImageSP.setImageResource(sp.getIdAnhSanPham());
            boolean isFavorite = dao_sanPhamYeuThich.isFavorite(sp.getIdSanPham());
            holder.imageFavorite.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
        }
    }

    @Override
    public int getItemCount() {
        return listSP.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView txtGiaSP;
        public TextView txtTenSP;
        public ImageView imageSP;

        public RoundedImageView roundedImageSP;

        public ImageButton imageFavorite;



        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            txtGiaSP = (TextView) itemView.findViewById(R.id.txtGiaSP);
            txtTenSP = (TextView) itemView.findViewById(R.id.txtTenSP);
            //imageSP = (ImageView) itemView.findViewById(R.id.imageSP);
            roundedImageSP=(RoundedImageView) itemView.findViewById(R.id.imageSP);
            imageFavorite = (ImageButton) itemView.findViewById(R.id.imageFavorite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int pos= getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            SanPham temp = listSP.get(pos);
                            listener.onItemClicked(temp);
                        }
                    }
                }
            });

            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        SanPham tempSP = listSP.get(pos);
                        boolean isFavorite = dao_sanPhamYeuThich.isFavorite(tempSP.getIdSanPham());
                        if (isFavorite) {
                            dao_sanPhamYeuThich.removeFromFavorites(tempSP.getIdSanPham());
                            imageFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
                            Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        } else {
                            dao_sanPhamYeuThich.addToFavorites(tempSP.getIdSanPham());
                            imageFavorite.setImageResource(R.drawable.baseline_favorite_24);
                            Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
