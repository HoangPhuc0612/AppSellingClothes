package com.example.doanfashionapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanfashionapp.Adapter.OrderDetailAdapter;
import com.example.doanfashionapp.DAO.DAO_Order_Details;
import com.example.doanfashionapp.DTO.Order_Detail;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private ListView listViewOrderDetails;
    private OrderDetailAdapter orderDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        listViewOrderDetails = findViewById(R.id.listViewOrderDetails);
        Button btnQuayLai = findViewById(R.id.btnquaylai);
        // Lấy orderId từ intent
        String orderId = getIntent().getStringExtra("ORDER_ID");

        // Load danh sách chi tiết đơn hàng từ database
        DAO_Order_Details daoOrderDetails = new DAO_Order_Details(this);
        List<Order_Detail> orderDetails = daoOrderDetails.getOrderDetailsByOrderId(orderId);

        // Set up adapter
        orderDetailAdapter = new OrderDetailAdapter(this, R.layout.item_order_detail, orderDetails);
        listViewOrderDetails.setAdapter(orderDetailAdapter);

        btnQuayLai.setOnClickListener(v -> finish());
    }
}
