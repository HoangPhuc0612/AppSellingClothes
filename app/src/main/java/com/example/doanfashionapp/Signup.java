package com.example.doanfashionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanfashionapp.DAO.DAO_Account;
import com.example.doanfashionapp.DTO.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.sql.SQLException;

public class Signup extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword, signupSDT, signupDiaChi;
    TextView loginRedirectText;
    Button btnSignup;
    FirebaseDatabase database;
    DatabaseReference reference;
    DAO_Account daoAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupSDT = findViewById(R.id.signup_sdt);
        signupDiaChi = findViewById(R.id.signup_diaChi);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        btnSignup = findViewById(R.id.btnSignup);
        daoAccount=new DAO_Account(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("accounts");
                String username = signupUsername.getText().toString();
                String email = signupEmail.getText().toString();
                String name = signupName.getText().toString();
                String sdt = signupSDT.getText().toString();
                String diaChi = signupDiaChi.getText().toString();
                String password = signupPassword.getText().toString();

                Account account = new Account(username, email, name, sdt, diaChi, password);
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            signupUsername.setError("Tên đăng nhập này đã tồn tại");

                        } else {
                            signupUsername.setError(null);
                            daoAccount.addToAccounts(username, email, name, "user", sdt, diaChi, password);
                            reference.child(username).setValue(account);
                            Toast.makeText(Signup.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Signup.this, Login.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
    }
}