package com.example.md18_and102_asm.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.md18_and102_asm.Account.ForgotPassword;
import com.example.md18_and102_asm.Account.LoginActivity;
import com.example.md18_and102_asm.R;

public class AccountFragment extends Fragment {

    public AccountFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnChangpw = view.findViewById(R.id.btnChangpw);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        // Create an Intent to start the LoginActivity
                Intent intent = new Intent(getContext(), LoginActivity.class);

                // Start the LoginActivity
                startActivity(intent);

                // Clear the SharedPreferences data
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataAc", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();

                // Finish the current Activity
                getActivity().finish();

            }
        });

        btnChangpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an Intent to start the LoginActivity
                Intent intent = new Intent(getContext(), ForgotPassword.class);

                // Start the LoginActivity
                startActivity(intent);
                // Finish the current Activity
                getActivity().finish();

            }
        });

//       Inflate the layout for this fragment
        // Lấy đối tượng SharedPreferences cho tệp "dataAC"
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataAc", Context.MODE_PRIVATE);

        // Lấy dữ liệu username từ đối tượng SharedPreferences
        String username = sharedPreferences.getString("username","");
        String position = sharedPreferences.getString("position","");

        // Đặt văn bản của TextView tvNameAccount thành dữ liệu username
        TextView tvNameAccount = view.findViewById(R.id.tvNameAccount);
        TextView tvpositionAccount = view.findViewById(R.id.tvpositionAccount);


        tvNameAccount.setText(username);
        tvpositionAccount.setText(position);
        return view;
    }
}