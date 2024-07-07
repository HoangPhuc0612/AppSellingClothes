package com.example.doanfashionapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanfashionapp.Adapter.SanPhamThanhToanGioHang_Adapter;
import com.example.doanfashionapp.DAO.DAO_Cart;
import com.example.doanfashionapp.DAO.DAO_Order_Details;
import com.example.doanfashionapp.DAO.DAO_Orders;
import com.example.doanfashionapp.DAO.DAO_ProductVariations;
import com.example.doanfashionapp.DTO.CartItem;
import com.example.doanfashionapp.DTO.Order;
import com.example.doanfashionapp.DTO.Order_Detail;
import com.example.doanfashionapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_XuLyThanhToanGioHang extends AppCompatActivity {
    private static final int SHIPPING_FEE = 30000;
    private RecyclerView recyclerView;
    private SanPhamThanhToanGioHang_Adapter adapter;
    private ArrayList<CartItem> selectedItems;
    private EditText edtDiaChi;
    private RadioGroup radioGroupThanhToan;
    private RadioButton radioTienMat, radioChuyenKhoan;
    private TextView txtTongTien;
    private Button btnXacNhanTT;
    private DAO_Orders daoOrders;
    private DAO_Order_Details daoOrderDetails;
    private DAO_Cart daoCart;
    private DAO_ProductVariations daoProductVariations;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_ly_thanh_toan_gio_hang);

        // Khởi tạo views
        recyclerView = findViewById(R.id.recycler_TTGioHang);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        radioGroupThanhToan = findViewById(R.id.radioGroupThanhToan);
        radioTienMat = findViewById(R.id.radioTienMat);
        radioChuyenKhoan = findViewById(R.id.radioChuyenKhoan);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnXacNhanTT = findViewById(R.id.btnXacNhanTT);
        backButton = findViewById(R.id.action_back);

        daoOrders = new DAO_Orders(this);
        daoOrderDetails = new DAO_Order_Details(this);
        daoCart = new DAO_Cart(this);
        daoProductVariations = new DAO_ProductVariations(this);

        // Nhận danh sách các sản phẩm được chọn từ intent
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("selectedItems")) {
            selectedItems = extras.getParcelableArrayList("selectedItems");
        }

        // Thiết lập RecyclerView
        adapter = new SanPhamThanhToanGioHang_Adapter(selectedItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tính tổng tiền
        int totalPrice = 0;
        for (CartItem item : selectedItems) {
            totalPrice += item.getSanPham().getGiaSanPham() * item.getQuantity();
        }

        int finalTotalPrice = totalPrice + SHIPPING_FEE;
        txtTongTien.setText("Tổng tiền: " + finalTotalPrice + " VNĐ");

        btnXacNhanTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment(finalTotalPrice);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", ""); // Trả về username hoặc chuỗi rỗng nếu không tìm thấy
    }

    private void handlePayment(int totalPrice) {
        // Nhập địa chỉ và kiểm tra hình thức thanh toán
        String address = edtDiaChi.getText().toString().trim();
        if (address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedPaymentMethodId = radioGroupThanhToan.getCheckedRadioButtonId();
        if (selectedPaymentMethodId == -1) {
            Toast.makeText(this, "Vui lòng chọn hình thức thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy ngày hiện tại
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Lấy username hiện tại từ SharedPreferences
        String username = getCurrentUsername();

        // Tạo và lưu Order
        String orderId = String.valueOf(System.currentTimeMillis()); // Tạo ID đơn hàng duy nhất
        Order order = new Order(orderId, username, currentDate, totalPrice); // "currentUsername" nên được thay bằng tên người dùng hiện tại
        daoOrders.insertOrder(order);

        // Tạo và lưu Order Details
        for (CartItem item : selectedItems) {
            String productVariationId = item.getProduct_variation_id();
            int quantity = item.getQuantity();
            int subtotal = quantity * item.getSanPham().getGiaSanPham();

            Order_Detail orderDetail = new Order_Detail(orderId, productVariationId, quantity, subtotal);
            daoOrderDetails.insertOrderDetail(orderDetail);

            int currentQuantity = daoProductVariations.getProductVariationQuantity(productVariationId);
            int newQuantity = currentQuantity - quantity;
            daoOrderDetails.updateProductVariationQuantity(productVariationId, newQuantity);

            // Xóa sản phẩm khỏi giỏ hàng sau khi đã được thanh toán
            daoCart.removeFromCart(item.getProduct_variation_id());
        }

        // Hiển thị thông báo thành công
        Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();

        // Quay lại màn hình giỏ hàng hoặc trang chủ
        finish();
    }
}
