package com.example.doanfashionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanfashionapp.DAO.DAO_Images;
import com.example.doanfashionapp.DAO.DAO_Order_Details;
import com.example.doanfashionapp.DTO.Order_Detail;
import com.example.doanfashionapp.R;

import java.util.List;
import java.util.Map;

public class OrderDetailAdapter extends ArrayAdapter<Order_Detail> {

    private Context mContext;
    private int mResource;
    private DAO_Order_Details daoOrderDetails;
    private DAO_Images daoImages;

    public OrderDetailAdapter(Context context, int resource, List<Order_Detail> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        daoOrderDetails = new DAO_Order_Details(context);
        daoImages = new DAO_Images(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        ImageView imgProduct = view.findViewById(R.id.imgProduct);
        TextView txtProductName = view.findViewById(R.id.txtProductName);
        TextView txtQuantity = view.findViewById(R.id.txtQuantity);
        TextView txtSubtotal = view.findViewById(R.id.txtSubtotal);
        TextView txtSize = view.findViewById(R.id.txtSize);
        TextView txtColor = view.findViewById(R.id.txtColor);

        Order_Detail orderDetail = getItem(position);

        if (orderDetail != null) {
            txtQuantity.setText("Số lượng: " + orderDetail.getQuantity());
            txtSubtotal.setText("Tổng tiền: " + orderDetail.getSubtotal());
            txtProductName.setText(orderDetail.getProductVariationId());

            String productVariationId = orderDetail.getProductVariationId();
            Map<String, String> productDetails = daoOrderDetails.getProductVariationDetails(productVariationId);
            String size = productDetails.get("SIZE");
            String color = productDetails.get("COLOR");
            txtSize.setText("Kích thước: " + size);
            txtColor.setText("Màu sắc: " + color);

            String productId = daoOrderDetails.getProductIdFromVariationId(productVariationId);
            int productImage = daoImages.getFirstImageByProductId(productId);
            imgProduct.setImageResource(productImage);
        }

        return view;
    }
}
