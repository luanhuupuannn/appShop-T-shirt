package com.example.md18_and102_asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.model.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Note> list;

    private ArrayList<Note> filteredList;


    private final ShirtDao movieDao;
    NoteAdapter adapter = this;
    SQLiteDatabase db;

    public NoteAdapter(Context context, ArrayList<Note> list, ShirtDao movieDao) {
        this.context = context;
        this.list = list;
        this.movieDao = movieDao;
    }
    public void setFilter(ArrayList<Note> filterlist) {
        filteredList = filterlist;
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật lại danh sách hiển thị
    }


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_note, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        //set dữ liệu lên từng item trên recycler
        holder.tvnamemovietry.setText(list.get(position).getNamemovietry());
        holder.tvtime.setText(list.get(position).getTime());
        holder.tvidchair.setText(list.get(position).getIdchair());
        holder.tvQuaitly.setText(String.valueOf(list.get(position).getQuantity()));
        holder.tvPrice.setText(String.valueOf(list.get(position).getPrice()));

//        holder.icDeleteSP1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int adapterPosition = holder.getAdapterPosition();
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    deleteItem(adapterPosition);
//                }
//            }
//        });
    }

    public void deleteItem(int position) {
        Note note = list.get(position);
        movieDao.delete(note); // Gọi phương thức xóa trong DAO
        list.remove(position); // Xóa mục từ danh sách
        notifyItemRemoved(position); // Thông báo cho RecyclerView về sự thay đổi
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public   TextView  tvnamemovietry,tvQuaitly,tvidchair,tvtime,tvPrice;
        public ImageView imgViewSP, icAddSP, icDeleteSP1;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            //
            tvnamemovietry = itemView.findViewById(R.id.tvnamemovietry);
            tvQuaitly = itemView.findViewById(R.id.tvQuaitly);
            tvidchair = itemView.findViewById(R.id.tvidchair);
            tvtime = itemView.findViewById(R.id.tvtime);

//            icDeleteSP1 = itemView.findViewById(R.id.icDeleteSP1);

            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
