package com.example.md18_and102_asm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.activity.GetChairActivity;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.model.Shirt;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieUserAdapter extends RecyclerView.Adapter<MovieUserAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Shirt> list;
    private final ShirtDao movieDao;


    //Constructor


    // hamf search
    public MovieUserAdapter(Context context, ArrayList<Shirt> list, ShirtDao movieDao) {
        this.context = context;
        this.list = list;
        this.movieDao = movieDao;
    }

    public void setFilter(ArrayList<Shirt> filterlist) {
        this.list = filterlist;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MovieUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_itemuser_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieUserAdapter.ViewHolder holder, int position) {
        //set dữ liệu lên từng item trên recycler
        holder.tvNameMovie.setText(list.get(position).getNameshirt());
        holder.tvCategoryandtime.setText(list.get(position).getDescribe());
        holder.tvPremieretime.setText(list.get(position).getInventory());

        SharedPreferences sharedPreferences = context.getSharedPreferences("dataAc", Context.MODE_PRIVATE);
        // Lấy dữ liệu username từ đối tượng SharedPreferences
        String usernameac = sharedPreferences.getString("username","");

          // bắt sự kiện book ticket
        holder.btnBookticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usernameac == ""){
                    Toast.makeText(context, "bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();

                }
                else {

                    Shirt bookticket = list.get(holder.getAdapterPosition());
                    bookticket(bookticket);
                }

            }
        });
        //lấy ảnh từ drawable
        int imgId = ((Activity)context).getResources().getIdentifier(
                list.get(position).getAvatar(),"drawable",
                ((Activity)context).getPackageName());
        holder.imgViewSP.setImageResource(imgId);
        //lấy ảnh từ Internet
        if (list.get(position).getAvatar().startsWith("http://")||
                list.get(position).getAvatar().startsWith("https://")){
            Picasso.get().load(list.get(position).getAvatar()).into(holder.imgViewSP);
        }
    }

    private void bookticket(Shirt booticket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_book_ticket, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        // ánh xạ
        TextView tvNameMovieBookTicket = view.findViewById(R.id.tvnamemoviebooktiket);
        Button btnCong = view.findViewById(R.id.btn_cong);
        Button btnTru = view.findViewById(R.id.btn_tru);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvPay = view.findViewById(R.id.tvPay);
        Spinner spinner = view.findViewById(R.id.autoComplete);
        Button btnSelected = view.findViewById(R.id.btnslected);
        ImageView imgAvatar = view.findViewById(R.id.imgavta);
        TextView tvSoLuong = view.findViewById(R.id.tvsoluong);

        // đổ dữ liệu của tên phim đã chọn
        tvNameMovieBookTicket.setText(booticket.getNameshirt());

        // sét ảnh lên
        int avatarID = context.getResources().getIdentifier(booticket.getAvatar(), "drawable", context.getPackageName());
        imgAvatar.setImageResource(avatarID);
        // Set up spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>((Activity) context, android.R.layout.simple_spinner_dropdown_item, new String[]{"S", "L", "XL", "XXL"});
        spinner.setAdapter(adapter);
        //
        int[] soLuong = {1};
        btnCong.setOnClickListener(v -> {
            soLuong[0]++;
            tvSoLuong.setText(String.valueOf(soLuong[0]));
            // Update the text of tvPay
            tvPay.setText(String.valueOf(soLuong[0] * 100000));
        });
        btnTru.setOnClickListener(v -> {
            if (soLuong[0] > 0) {
                soLuong[0]--;
                tvSoLuong.setText(String.valueOf(soLuong[0]));
                // Update the text of tvPay
                tvPay.setText(String.valueOf(soLuong[0] * 100000));
            }
        });


        btnSelected.setOnClickListener(v -> {
            // Tạo mảng để lưu trữ dữ liệu đã chọn
            String[] selectedData = new String[5];
            selectedData[0] = tvNameMovieBookTicket.getText().toString();
            selectedData[1] = spinner.getSelectedItem().toString();

            selectedData[3] = tvSoLuong.getText().toString();
            selectedData[4] =tvPay.getText().toString();

            // Truyền dữ liệu tên, số lượng , thời trang đã chọn đến intent của GetChairActivity
            Intent intent = new Intent((Activity) context, GetChairActivity.class);
            intent.putExtra("selectedData", selectedData);
            // chuyển kênh ngay
            ((Activity) context).startActivity(intent);

        });
        // Show the dialog
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNameMovie, tvCategoryandtime, tvPremieretime;
        public ImageView imgViewSP, icAddSP, icDeleteSP;
        public RelativeLayout icEditSP;
        public Button btnBookticket;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Ánh xạ
            tvNameMovie = itemView.findViewById(R.id.tvNameMovie);
            tvCategoryandtime = itemView.findViewById(R.id.tvCategoryandtime);
            tvPremieretime = itemView.findViewById(R.id.tvPremieretime);
            imgViewSP = itemView.findViewById(R.id.imgViewSP);
            icAddSP = itemView.findViewById(R.id.icAddSP);
            icEditSP = itemView.findViewById(R.id.icEditSP);
            icDeleteSP = itemView.findViewById(R.id.icDeleteSP);
            btnBookticket = itemView.findViewById(R.id.btnBookticket);
        }
    }
}
