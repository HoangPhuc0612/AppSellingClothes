package com.example.doanfashionapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.MenuProvider;

import com.example.doanfashionapp.ActivityDanhGia;
import com.example.doanfashionapp.EditProfile;
import com.example.doanfashionapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_Bottom_NavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Bottom_NavFragment extends Fragment implements MenuProvider {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtTenNguoiDung,txtEmailNguoiDung,item_name1,item_name2,item_number1,item_number2;

    private TextView txtTen,txtEmail,txtAddress,txtPhoneNumber;

    private ImageView item1,item2;
    private String pass,ten,email,sdt,diaChi,username;

    public Profile_Bottom_NavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_Bottom_NavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_Bottom_NavFragment newInstance(String param1, String param2) {
        Profile_Bottom_NavFragment fragment = new Profile_Bottom_NavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile__bottom__nav, container, false);
        MenuHost menuHost= requireActivity();
        menuHost.addMenuProvider(this,getViewLifecycleOwner(),Lifecycle.State.RESUMED);
        txtEmailNguoiDung=(TextView) view.findViewById(R.id.txtEmailNguoiDung);
        txtTenNguoiDung=(TextView) view.findViewById(R.id.txtTenNguoiDung);
        item1=(ImageView) view.findViewById(R.id.imageItem1);
        item2=(ImageView) view.findViewById(R.id.imageItem2);
        item_name1=(TextView) view.findViewById(R.id.txtNameItem1);
        item_name2=(TextView) view.findViewById(R.id.txtNameItem2);
        item_number1=(TextView) view.findViewById(R.id.txtNumberItem1);
        item_number2=(TextView) view.findViewById(R.id.txtNumberItem2);

        txtTen=(TextView) view.findViewById(R.id.txtTen);
        txtEmail=(TextView) view.findViewById(R.id.txtEmail);
        txtAddress=(TextView) view.findViewById(R.id.txtAddress);
        txtPhoneNumber=(TextView) view.findViewById(R.id.txtPhoneNumber);

        getAccountInfo();
        item1.setImageResource(R.drawable.baseline_shopping_bag_24);
        item2.setImageResource(R.drawable.baseline_star_rate_24);
        item_name1.setText("Order");
        item_name2.setText("Reviewes");
        item_number1.setText("10+");
        item_number2.setText("4.5k");

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityDanhGia.class);
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // Nhận trung bình số sao từ Intent
                float averageRating = data.getFloatExtra("averageRating", 0.0f);

                // Cập nhật trung bình số sao lên item_number2
                item_number2.setText(String.valueOf(averageRating));
            }
        }
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.settings_menu,menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.itemEdit){
            Intent intent=new Intent(getContext(), EditProfile.class);
            intent.putExtra("hoten",txtTen.getText());
            intent.putExtra("email",txtEmail.getText());
            intent.putExtra("soDienThoai",txtPhoneNumber.getText());
            intent.putExtra("diaChi",txtAddress.getText());
            intent.putExtra("username",txtTenNguoiDung.getText());
            intent.putExtra("password_hash",pass);
            startActivity(intent);
        }

        return false;
    }

    private void getAccountInfo() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        username=sharedPreferences.getString("username", "");
        txtTenNguoiDung.setText(username); // Trả về username hoặc chuỗi rỗng nếu không có giá trị
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("accounts");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ten=snapshot.child(username).child("hoTen").getValue(String.class);
                email=snapshot.child(username).child("email").getValue(String.class);
                diaChi=snapshot.child(username).child("diaChi").getValue(String.class);
                sdt=snapshot.child(username).child("soDienThoai").getValue(String.class);
                pass=snapshot.child(username).child("password").getValue(String.class);
                txtTen.setText(ten);
                txtEmailNguoiDung.setText(email);
                txtEmail.setText(email);
                txtAddress.setText(diaChi);
                txtPhoneNumber.setText(sdt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}