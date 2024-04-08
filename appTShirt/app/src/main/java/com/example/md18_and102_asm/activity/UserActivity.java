package com.example.md18_and102_asm.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.adapter.MovieAdapter;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.fragment.AccountFragment;
import com.example.md18_and102_asm.fragment.HomeUserFragment;
import com.example.md18_and102_asm.fragment.NoteFragment;
import com.example.md18_and102_asm.model.Shirt;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    BottomNavigationView Bottom_nav;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    ImageView imgAdd;
    ShirtDao sanPhamDAO;
    MovieAdapter adapter;
    ArrayList<Shirt> listSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bottom_nav = findViewById(R.id.Bottom_nav);

        Bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                if (item.getItemId()==R.id.actionHome){
                    fragment = new HomeUserFragment();
                } else if (item.getItemId()==R.id.actionNote) {
                    fragment = new NoteFragment();
                }else {
                    fragment = new AccountFragment();
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();


                return true;
            }
        });


    }

}