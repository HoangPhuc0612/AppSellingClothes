package com.example.doanfashionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doanfashionapp.DTO.Order;
import com.example.doanfashionapp.R;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context context;
    private int resource;
    private List<Order> orders;

    public OrderAdapter(Context context, int resource, List<Order> orders) {
        super(context, resource, orders);
        this.context = context;
        this.resource = resource;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Order order = orders.get(position);

        TextView tvOrderId = convertView.findViewById(R.id.txtOrderID);
        TextView tvUsername = convertView.findViewById(R.id.txtUsername);
        TextView tvOrderDate = convertView.findViewById(R.id.txtOrderDate);
        TextView tvTotalPrice = convertView.findViewById(R.id.txtTotalPrice);

        tvOrderId.setText("Mã hóa đơn: " + order.getOrderId());
        tvUsername.setText("Tên tài khoản: " + order.getUsername());
        tvOrderDate.setText("Ngày: " + order.getOrderDate());
        tvTotalPrice.setText("Tổng tiền: " + order.getTotalPrice());

        return convertView;
    }
}

