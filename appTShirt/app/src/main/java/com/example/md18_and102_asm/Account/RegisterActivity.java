package com.example.md18_and102_asm.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText edtUserNameRegister;
    TextInputEditText edtFullNameRegister;
    TextInputEditText edtPasswordRegister;
    TextInputEditText edtConfirmPasswordRegister;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Ánh xạ
        edtUserNameRegister = findViewById(R.id.userNameRegister);
        edtFullNameRegister = findViewById(R.id.fullNameRegister);
        edtPasswordRegister = findViewById(R.id.passwordRegister);
        edtConfirmPasswordRegister = findViewById(R.id.confirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        //Bắt sự kiện click vào tvLogin
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        //Bắt sự kiện click button Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtUserNameRegister.getText().toString();
                String hoTen = edtFullNameRegister.getText().toString();
                String matKhau = edtPasswordRegister.getText().toString();
                String confirmPassword = edtConfirmPasswordRegister.getText().toString();

                DbHelper dbHelper = new DbHelper(RegisterActivity.this);
                //Kiểm tra việc nhập liệu
                if (tenDangNhap.isEmpty()   ){
                    Toast.makeText(RegisterActivity.this, "Please fill username details", Toast.LENGTH_SHORT).show();
                }
                else if( hoTen.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill fullname details", Toast.LENGTH_SHORT).show();

                }
                else if( matKhau.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill password details", Toast.LENGTH_SHORT).show();


                }
                else if( confirmPassword.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill confimpassword details", Toast.LENGTH_SHORT).show();

                }else {
                    boolean check = dbHelper.checkUsername(tenDangNhap);
                    if (check){
                        edtUserNameRegister.setError("Username already exists...!!");
                    }else {
                        if (confirmPassword.compareTo(matKhau) == 0){
                            if (isValid(matKhau)){
                                dbHelper.register(tenDangNhap,matKhau,hoTen);
                                Toast.makeText(RegisterActivity.this, "Record inserted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            }else {
                                Toast.makeText(RegisterActivity.this, "Password must at least 8 characters, having letter, digit, and special symbol",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "Confirm Password and Password didn't match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    //Hàm kiểm tra tính hợp lệ của password
    private static boolean isValid(String passwordHere) {
        int flag1 = 0, flag2 = 0, flag3 = 0;
        if (passwordHere.length() < 0){
            return false;
        }else {
            for (int p = 0; p < passwordHere.length(); p++){
                //Kiểm tra ký tự hiện tại có phải là một chữ cái hay không
                if (Character.isLetter(passwordHere.charAt(p))){
                    flag1 = 1;
                }
            }
            for (int r = 0; r < passwordHere.length(); r++){
                //Kiểm tra ký tự hiện tại có phải là một chữ số hay không
                if (Character.isDigit(passwordHere.charAt(r))){
                    flag2 = 1;
                }
            }
            for (int s = 0; s < passwordHere.length(); s++){
                //lấy ký tự hiện tại
                char c = passwordHere.charAt(s);
                //--và kiểm tra xem nó có thuộc các ký tự đặc biệt như dấu chấm câu
                //(33-46) hoặc ký tự '@' (64) hay không
                if (c >= 33 && c <= 46 || c == 64){
                    flag3 = 1;
                }
            }
            if (flag1 == 1 && flag2 == 1 && flag3 == 1){
                return true;
            }else {
                return false;
            }
        }
    }
}