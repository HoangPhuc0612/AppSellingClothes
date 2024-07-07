package com.example.doanfashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityDanhGia extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView tvNumberOfRatings;
    private TextView tvAverageRating;
    private int numberOfRatings = 0; // Biến để lưu trữ số lượng đánh giá
    private float totalStars = 0; // Biến để lưu trữ tổng số sao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);

        ratingBar = findViewById(R.id.ratingBar);
        tvNumberOfRatings = findViewById(R.id.tvNumberOfRatings);
        tvAverageRating = findViewById(R.id.tvAverageRating);

        // Nếu cần xác định xử lý sự kiện khi người dùng đánh giá,
        // bạn có thể thêm Listener cho RatingBar ở đây.

        // Lấy giá trị hiện tại của RatingBar
        float currentRating = ratingBar.getRating();

        // Tăng giá trị của RatingBar lên một đơn vị
        ratingBar.setRating(currentRating + 1);

        // Cập nhật số lượng đánh giá và tổng số sao
        numberOfRatings++;
        totalStars += (currentRating + 1);

        // Hiển thị số lượng đánh giá lên TextView
        tvNumberOfRatings.setText("Số lượng đánh giá: " + numberOfRatings);

        // Tính trung bình số sao và hiển thị lên TextView
        float averageRating = totalStars / numberOfRatings;
        tvAverageRating.setText("Trung bình số sao: " + averageRating);

        Button btnSend = findViewById(R.id.btnSend);

        Button btnThoat = findViewById(R.id.btnThoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện các hành động khi nhấn vào nút "Thoát"
                // Ví dụ: Đóng ActivityDanhGia
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy số sao mà người dùng đã chọn
                float currentRating = ratingBar.getRating();

                // Thêm số sao mà người dùng đã chọn vào tổng số sao
                totalStars += currentRating;

                // Cập nhật số lượng đánh giá lên TextView
                numberOfRatings++;

                // Tính trung bình số sao
                float averageRating = totalStars / numberOfRatings;

                // Tạo Intent để gửi trung bình số sao về Profile_Bottom_NavFragment
                Intent resultIntent = new Intent();
                resultIntent.putExtra("averageRating", averageRating);

                // Gửi kết quả trở lại Profile_Bottom_NavFragment
                setResult(RESULT_OK, resultIntent);

                // Kết thúc ActivityDanhGia
                finish();
            }
        });
    }
}