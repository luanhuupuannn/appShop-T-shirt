package com.example.md18_and102_asm.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.adapter.NoteAdapter;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.model.Note;
import com.example.md18_and102_asm.model.Shirt;

import java.util.ArrayList;public class NoteFragment extends Fragment {
    RecyclerView rycNote;
    NoteAdapter noteAdapter;
    ShirtDao movieDao;
    ArrayList<Note> list;
    SearchView searchview1;

    public NoteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        movieDao = new ShirtDao(requireContext());
        list.addAll(movieDao.getListSanPham2());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        rycNote = view.findViewById(R.id.rycNote);
        searchview1 = view.findViewById(R.id.searchview1);

        rycNote.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        noteAdapter = new NoteAdapter(requireContext(), list, movieDao);
        rycNote.setAdapter(noteAdapter);

        searchview1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListus(newText);
                return true;
            }
        });

        return view;
    }

    private void filterListus(String text) {
        ArrayList<Note> filterlist = new ArrayList<>();
        for (Note note : list) {
            if (note.getNamemovietry().toLowerCase().contains(text.toLowerCase())) {
                filterlist.add(note);
            }
        }
        if (filterlist.isEmpty()) {
            // Hiển thị thông báo khi không có kết quả
            Toast.makeText(getContext(), "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
            // Xóa danh sách hiện tại trong RecyclerView
        } else {
            // Cập nhật RecyclerView với danh sách kết quả tìm kiếm mới
            noteAdapter.setFilter(filterlist);
        }
    }
}

