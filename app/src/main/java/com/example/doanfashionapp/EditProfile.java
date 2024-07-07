package com.example.doanfashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanfashionapp.Fragment.Profile_Bottom_NavFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {
    EditText editName, editEmail, editUsername, editPassword,edtDiaChi,edtSdt;
    Button saveButton;
    String nameUser, emailUser, usernameUser, passwordUser,diaChiUser,sdtUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        reference = FirebaseDatabase.getInstance().getReference("accounts");
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        edtDiaChi= findViewById(R.id.edtDiaChi);
        edtSdt= findViewById(R.id.edtSdt);
        saveButton = findViewById(R.id.saveButton);
        editUsername.setEnabled(false);
        showData();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isPasswordChanged() || isEmailChanged()||isAddressChanged()||isPhoneNumberChanged()){
                    Toast.makeText(EditProfile.this, "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "Không có sự thay đổi nào", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("hoten", nameUser);
                startActivity(intent);
            }
        });
    }
    private boolean isAddressChanged() {
        if (!diaChiUser.equals(edtDiaChi.getText().toString())){
            reference.child(usernameUser).child("diaChi").setValue(edtDiaChi.getText().toString());
            diaChiUser = edtDiaChi.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isPhoneNumberChanged() {
        if (!sdtUser.equals(edtSdt.getText().toString())){
            reference.child(usernameUser).child("soDienThoai").setValue(edtSdt.getText().toString());
            sdtUser = edtSdt.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isNameChanged() {
        if (!nameUser.equals(editName.getText().toString())){
            reference.child(usernameUser).child("hoTen").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isEmailChanged() {
        if (!emailUser.equals(editEmail.getText().toString())){
            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isPasswordChanged() {
        if (!passwordUser.equals(editPassword.getText().toString())){
            reference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    public void showData(){
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("hoten");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        passwordUser = intent.getStringExtra("password_hash");
        diaChiUser=intent.getStringExtra("diaChi");
        sdtUser=intent.getStringExtra("soDienThoai");
        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
        editPassword.setText(passwordUser);
        edtSdt.setText(sdtUser);
        edtDiaChi.setText(diaChiUser);
    }
}