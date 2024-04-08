package com.example.md18_and102_asm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.Account.Cartacti;
import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.adapter.MovieUserAdapter;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.model.Shirt;

import java.util.ArrayList;


public class HomeUserFragment extends Fragment {
    RecyclerView recyclerView;
    MovieUserAdapter sanPhamAdapter;
    ShirtDao sanPhamDAO;
    ArrayList<Shirt> listSanpham;
    SearchView searchview1;
    ImageView icCart;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listSanpham = new ArrayList<>();
        sanPhamDAO = new ShirtDao(requireContext());
        listSanpham.addAll(sanPhamDAO.getListSanPham());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);
        recyclerView = view.findViewById(R.id.frRecycler1);
        icCart= view.findViewById(R.id.icCart);
        searchview1 = view.findViewById(R.id.seaechview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        sanPhamAdapter = new MovieUserAdapter(requireContext(), listSanpham, sanPhamDAO);
        recyclerView.setAdapter(sanPhamAdapter);


        icCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Cartacti.class);
                startActivity(intent);

            }
        });

        // ham search
        searchview1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        searchview1.clearFocus();

        return view;


    }

    // ham search
    private void filterList(String text) {
        ArrayList<Shirt> filterlist = new ArrayList<>();
        for (Shirt movie :listSanpham){
            if(movie.getNameshirt().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(movie);
            }
        }
        if (filterlist.isEmpty()){
            Toast.makeText(getContext(), "chua co du lieu", Toast.LENGTH_SHORT).show();
        }
        else {
            sanPhamAdapter.setFilter(filterlist);
        }
    }

}




