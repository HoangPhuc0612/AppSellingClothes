package com.example.doanfashionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanfashionapp.Adapter.ColorAdapter;
import com.example.doanfashionapp.Adapter.ImagePagerAdapter;
import com.example.doanfashionapp.Adapter.SizeAdapter;
import com.example.doanfashionapp.DAO.DAO_Cart;
import com.example.doanfashionapp.DAO.DAO_HangSanPham;
import com.example.doanfashionapp.DAO.DAO_Images;
import com.example.doanfashionapp.DAO.DAO_LoaiSanPham;
import com.example.doanfashionapp.DAO.DAO_Order_Details;
import com.example.doanfashionapp.DAO.DAO_ProductVariations;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityThongTinSanPham extends AppCompatActivity {
    private TextView txtTenSP, txtGiaSP, txtLoaiSP, txtBrandSP, txtMota;
    private TextView txtColor, txtSize;
    private Button btnMua,btnQL,btnThemGH;
    private ImageButton btnPrevImage, btnNextImage;
    private ViewPager2 viewPagerImages;
    private ImagePagerAdapter imagePagerAdapter;
    private ArrayList<Integer> images;
    private int currentImageIndex = 0;
    private DAO_Images dao_images;
    private RecyclerView recyclerViewColors;
    private RecyclerView recyclerViewSizes;
    private ColorAdapter colorAdapter;
    private SizeAdapter sizeAdapter;
    private DAO_ProductVariations daoProductVariations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_san_pham);
        addControls();
        addEvents();
    }
    void addControls(){
        txtTenSP = findViewById(R.id.txtTenSP);
        txtGiaSP = findViewById(R.id.txtgiaSP);
        txtLoaiSP = findViewById(R.id.txtLoaiSP);
        txtBrandSP = findViewById(R.id.txtBrandSP);
        txtMota = findViewById(R.id.txt_mota);
        //anhSP = findViewById(R.id.anhSP);
        btnQL=findViewById(R.id.btn_quaylai);
        btnMua=findViewById(R.id.btn_mua);
        btnThemGH=findViewById(R.id.btn_themgiohang);
        btnPrevImage = findViewById(R.id.btnPrevImage);
        btnNextImage = findViewById(R.id.btnNextImage);
        viewPagerImages = findViewById(R.id.viewPagerImages);
        txtColor = findViewById(R.id.txt_color);
        txtSize = findViewById(R.id.txt_size);
        recyclerViewColors = findViewById(R.id.recyclerViewColors);
        recyclerViewSizes = findViewById(R.id.recyclerViewSizes);

        dao_images = new DAO_Images(this);
        daoProductVariations = new DAO_ProductVariations(this);
    }
    void addEvents(){
        Intent intent = getIntent();
        String maSP = intent.getStringExtra("maSP");
        txtTenSP.setText(intent.getStringExtra("tenSP"));
        int giaSP = intent.getIntExtra("giaSP", 0);
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        String formattedGiaSP = numberFormat.format(giaSP) + " đ";
        txtGiaSP.setText(formattedGiaSP);
        // LẤY ID MÃ LOẠI ĐỂ LẤY TÊN LOẠI SP ĐÓ
        String loaiSP = intent.getStringExtra("loaiSP");

        if (loaiSP != null && !loaiSP.isEmpty()) {
            DAO_LoaiSanPham daoLoaiSanPham = new DAO_LoaiSanPham(this);
            String tenLoaiSP = daoLoaiSanPham.getTenLoaiSanPham(loaiSP);
            txtLoaiSP.setText("Loại: " + tenLoaiSP);
        } else {
            txtLoaiSP.setText("Loại:Loai San pham này khong ton tai");
        }

        // Lấy ID hãng sản phẩm để lấy tên hãng sản phẩm
        String hangSP = intent.getStringExtra("brandSP");
        if (hangSP != null && !hangSP.isEmpty()) {
            DAO_HangSanPham daoHangSanPham = new DAO_HangSanPham(this);
            String tenHangSanPham = daoHangSanPham.getTenHangSanPham(hangSP);
            txtBrandSP.setText("Mùa ra mắt: " + tenHangSanPham);
        } else {
            txtBrandSP.setText("Mùa: Mùa này không tồn tại");
        }
        txtMota.setText(intent.getStringExtra("motaSP"));

        DAO_ProductVariations daoProductVariations = new DAO_ProductVariations(this);
        List<String> sizes = daoProductVariations.getSizesByProductId(maSP);
        List<String> colors = daoProductVariations.getColorsByProductId(maSP);

        colorAdapter = new ColorAdapter(colors);
        recyclerViewColors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewColors.setAdapter(colorAdapter);

        sizeAdapter = new SizeAdapter(sizes);
        recyclerViewSizes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSizes.setAdapter(sizeAdapter);



        ArrayList<Integer> images = dao_images.getImagesByProductId(maSP);
        if (images != null && !images.isEmpty()) {
            imagePagerAdapter = new ImagePagerAdapter(this, images);
            viewPagerImages.setAdapter(imagePagerAdapter);

            btnPrevImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentImageIndex > 0) {
                        currentImageIndex--;
                        viewPagerImages.setCurrentItem(currentImageIndex, true);
                    }
                }
            });

            btnNextImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentImageIndex < images.size() - 1) {
                        currentImageIndex++;
                        viewPagerImages.setCurrentItem(currentImageIndex, true);
                    }
                }
            });

            viewPagerImages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    currentImageIndex = position;
                }
            });
        }
        btnQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColor = colorAdapter.getSelectedColor();
                String selectedSize = sizeAdapter.getSelectedSize();

                if (selectedSize == null || selectedColor == null) {
                    Toast.makeText(ActivityThongTinSanPham.this, "Vui lòng chọn size và màu", Toast.LENGTH_SHORT).show();
                    return;
                }

                String productVariationId = daoProductVariations.getProductVariationId(maSP, selectedSize, selectedColor);
                int quantity = daoProductVariations.getProductVariationQuantity(productVariationId);

                if (quantity > 0) {
                    Intent intent = new Intent(getApplicationContext(), ActivityXuLiThanhToan.class);
                    intent.putExtra("maSP", maSP);
                    intent.putExtra("tenSP", txtTenSP.getText().toString());
                    intent.putExtra("giaSP", giaSP);
                    intent.putExtra("anhSP", getIntent().getIntExtra("anhSP", 0));
                    startActivity(intent);
                } else {
                    Toast.makeText(ActivityThongTinSanPham.this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String username = getIntent().getStringExtra("username");
//                String productId = getIntent().getStringExtra("productId");
//
////                Intent intent = new Intent(ActivityThongTinSanPham.this, Activity2DSDONHANG.class);
//                intent.putExtra("username", username);
//                intent.putExtra("productId", productId);
//                startActivity(intent);
                String selectedColor = colorAdapter.getSelectedColor();
                String selectedSize = sizeAdapter.getSelectedSize();

                if (selectedSize == null || selectedColor == null) {
                    Toast.makeText(ActivityThongTinSanPham.this, "Vui lòng chọn size và màu", Toast.LENGTH_SHORT).show();
                    return;
                }

                String productVariationId = daoProductVariations.getProductVariationId(maSP, selectedSize, selectedColor);
                int quantity = daoProductVariations.getProductVariationQuantity(productVariationId);

                if (quantity > 0) {
                    DAO_Cart daoCart = new DAO_Cart(ActivityThongTinSanPham.this);
                    daoCart.addToCart(productVariationId, 1);
                    Toast.makeText(ActivityThongTinSanPham.this, "Đã thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityThongTinSanPham.this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}