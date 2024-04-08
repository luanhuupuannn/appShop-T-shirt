package com.example.md18_and102_asm.Account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.activity.AdminActivity;
import com.example.md18_and102_asm.activity.UserActivity;
import com.example.md18_and102_asm.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUserNameLogin;
    TextInputEditText edtPasswordLogin;
    Button btnLogin;
    TextView tvForgotPassword;
    TextView tvRegister;
    CheckBox cbAdmin,cbuser;


    DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ánh xạ
        edtUserNameLogin = findViewById(R.id.UsernameLogin);
        edtPasswordLogin = findViewById(R.id.PasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
        cbAdmin = findViewById(R.id.AdminCB);
        cbuser = findViewById(R.id.UserCB);

        //Bắt sự kiện click vào tvForgotPassword
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

        //Bắt sự kiện click tvRegister
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //Bắt sự kiện click button Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserNameLogin.getText().toString();
                String password = edtPasswordLogin.getText().toString();
                DbHelper dbHelper = new DbHelper(LoginActivity.this);

                if(username.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill Username and password details", Toast.LENGTH_SHORT).show();

                }
                else {
                    if(username.isEmpty() || password.isEmpty()){

                        if (username.isEmpty()){
                            Toast.makeText(LoginActivity.this, "Please fill Username details", Toast.LENGTH_SHORT).show();
                        }
                        if (password.isEmpty()){
                            Toast.makeText(LoginActivity.this, "Please fill password details", Toast.LENGTH_SHORT).show();

                        }


                    }

                    else  if(username != null && password != null) {
                        if (dbHelper.login(username,password) == 1){
                            if (cbAdmin.isChecked() && cbuser.isChecked()) {
                                Toast.makeText(LoginActivity.this, "You can only choose 1 position", Toast.LENGTH_SHORT).show();
                                cbAdmin.setChecked(false);
                                cbuser.setChecked(false);
                            }
                            else  if  (cbAdmin.isChecked()){
                                // Kiểm tra và quản lý giỏ hàng (nếu có)

//                           dbHelper.migrateOrCreateCart(username);

//
                                Toast.makeText(LoginActivity.this, "Login vào ADMIN", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("dataAc", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username",username);
                                editor.putString("position","Admin");
                                editor.apply();//lưu dữ liệu vào data với key và value

                                startActivity(new Intent(LoginActivity.this, AdminActivity.class));



                            }
                            else if  (cbuser.isChecked()){
                                Toast.makeText(LoginActivity.this, "Login vào USER", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("dataAc", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username",username);
                                editor.putString("position","User");
                                editor.apply();//lưu dữ liệu vào data với key và value
                                startActivity(new Intent(LoginActivity.this, UserActivity.class));
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Bạn chưa chọn vai trò ", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(LoginActivity.this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }





            }
        });
    }
}