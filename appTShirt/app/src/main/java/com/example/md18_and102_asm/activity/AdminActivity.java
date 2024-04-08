package com.example.md18_and102_asm.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.fragment.AccountFragment;
import com.example.md18_and102_asm.fragment.HomeFragment;
import com.example.md18_and102_asm.fragment.NoteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView Bottom_nav;
    @SuppressLint("MissingInflatedId")
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
                    fragment = new HomeFragment();
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
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment)
                .commit();
    }
}