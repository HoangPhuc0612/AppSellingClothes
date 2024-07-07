package com.example.doanfashionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Login extends AppCompatActivity {
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    private String DB_NAME="FashionAppDB.db";
    private String DB_PATH="/databases/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SaoChepDB();
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }
    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Tên đăng nhập không tồn tại");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Mật khẩu không được bỏ trống");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("accounts");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);
                        String nameFromDB = snapshot.child(userUsername).child("hoTen").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                        String sdtFromDB = snapshot.child(userUsername).child("soDienThoai").getValue(String.class);
                        String diaChiFromDB = snapshot.child(userUsername).child("diaChi").getValue(String.class);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("hoten", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("sdt", sdtFromDB);
                        intent.putExtra("diaChi", diaChiFromDB);
                        startActivity(intent);

                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", usernameFromDB); // 'username' là tên người dùng đã đăng nhập
                        editor.putString("diaChi", diaChiFromDB);
                        editor.apply();
                    } else {
                        loginPassword.setError("Sai mật khẩu");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("Tên đăng nhập không tồn tại!");
                    loginUsername.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void SaoChepDB(){
        File dbFile=getDatabasePath(DB_NAME);
        if (!dbFile.exists()){
            copy();
            Toast.makeText(getApplicationContext(),"Sao chép dữ liệu thành công",Toast.LENGTH_LONG).show();
        }
    }

    private void copy() {
        try {
            InputStream myInput=getAssets().open(DB_NAME);
            String outFileName=getApplicationInfo().dataDir+DB_PATH+DB_NAME;
            File f= new File(getApplicationInfo().dataDir+DB_PATH);
            if (!f.exists()){
                f.mkdir();
            }
            OutputStream myOutPut=new FileOutputStream(outFileName);
            byte[] buffer=new byte[1024];
            int len;
            while((len=myInput.read(buffer))>0){
                myOutPut.write(buffer,0,len);

            }
            myOutPut.flush();
            myInput.close();
            myOutPut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e("Lỗi sao chép",toString());
        }
    }
}