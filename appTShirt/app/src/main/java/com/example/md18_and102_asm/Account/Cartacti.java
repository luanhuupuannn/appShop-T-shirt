package com.example.md18_and102_asm.Account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.adapter.CartAdapter;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.model.Cart;

import java.util.ArrayList;

public class Cartacti extends AppCompatActivity implements CartAdapter.TotalValueListener {
    RecyclerView ryccart;
    CartAdapter cartAdapter;
    ShirtDao cartdao;
    TextView tonggia;

    public int tongtong;
    LoginActivity loginActivity;
    ArrayList<Cart> listc;

    private CartAdapter.TotalValueListener totalValueListener;


    CartAdapter adapter = new CartAdapter(this, listc, cartdao,totalValueListener);

    @Override
    public void onTotalValueChanged(int total) {
        tonggia.setText(String.valueOf(total));  // Cập nhật TextView tonggia
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cartacti);
        tonggia= findViewById(R.id.tonggia);

        listc = new ArrayList<>();
        cartdao = new ShirtDao(this);
        listc.addAll(cartdao.getListgiohang());

        ryccart = findViewById(R.id.ryccart);  // Tìm kiếm view trực tiếp trong Activity

        ryccart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        CartAdapter cartAdapter = new CartAdapter(this, listc, cartdao, this); // Truyền "this" làm trình nghe
        ryccart.setAdapter(cartAdapter);

        int totalValue = getIntent().getIntExtra("total", 0);  // Default to 0 if not found

        String totalValueString = String.valueOf(totalValue);  // Convert int to String
        tonggia.setText(totalValueString);

    }

}