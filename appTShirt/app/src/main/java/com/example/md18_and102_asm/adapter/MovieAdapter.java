package com.example.md18_and102_asm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private final Context context;
    private ArrayList<Shirt> list;

    private final ShirtDao movieDao;
    MovieAdapter adapter = this;
    SQLiteDatabase db;



 // hamf search
    public MovieAdapter(Context context, ArrayList<Shirt> list, ShirtDao movieDao) {
        this.context = context;
        this.list = list;
        this.movieDao = movieDao;
    }

    public void setFilter(ArrayList<Shirt> filterlist) {
        this.list = filterlist;
        notifyDataSetChanged();
    }











    
    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_recycler, parent, false);



        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
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
        int imgId = ((Activity) context).getResources().getIdentifier(
                list.get(position).getAvatar(), "drawable",
                ((Activity) context).getPackageName());
        holder.imgViewSP.setImageResource(imgId);
        //lấy ảnh từ Internet
        if (list.get(position).getAvatar().startsWith("http://") ||
                list.get(position).getAvatar().startsWith("https://")) {
            Picasso.get().load(list.get(position).getAvatar()).into(holder.imgViewSP);
        }
        //Bắt sự kiện icDelete
        holder.icDeleteSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.ic_warning);
                builder.setMessage("Bạn có chắc chắn muốn xoá sản phẩm '" +
                        list.get(holder.getAdapterPosition()).getNameshirt() + "' không?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int masp = list.get(holder.getAdapterPosition()).getIdmv();
                        boolean check = movieDao.deleteSanPham(masp);
                        if (check) {
                            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = movieDao.getListSanPham();
                            notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Failed Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        //Bắt sự kiện icEdit
        holder.icEditSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shirt sanPham = list.get(holder.getAdapterPosition());
                dialogUpdateSanPham(sanPham);

            }
        });
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

            // Tạo bundle để lưu trữ dữ liệu đã chọn



        });
        // Show the dialog
        dialog.show();
    }
    private void dialogUpdateSanPham(Shirt sanPhamUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        //Ánh xạ các widget
        TextInputEditText edtTenSPEdit = view.findViewById(R.id.edtTenSPEdit);
        TextInputEditText edtGiaBanEdit = view.findViewById(R.id.edtGiaBanEdit);
        TextInputEditText edtSoLuongEdit = view.findViewById(R.id.edtSoLuongEdit);
        TextInputEditText edtLinkEdit = view.findViewById(R.id.edtLinkEdit);

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        //set dữ liệu lên các edt để lấy giá trị cũ của SanPham cần update
        edtTenSPEdit.setText(sanPhamUpdate.getNameshirt());
        edtGiaBanEdit.setText(String.valueOf(sanPhamUpdate.getDescribe()));
        edtSoLuongEdit.setText(String.valueOf(sanPhamUpdate.getInventory()));
        edtLinkEdit.setText(sanPhamUpdate.getAvatar());

        //Bắt sự kiện cho btnUpdate
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy dữ liệu từ các ô nhập trong dialog
                String title = edtTenSPEdit.getText().toString();
                String price = edtGiaBanEdit.getText().toString();
                String quantity = edtSoLuongEdit.getText().toString();
                String linkEdit = edtLinkEdit.getText().toString();

                Shirt sp = new Shirt();
                sp.setIdmv(sanPhamUpdate.getIdmv());
                sp.setNameshirt(title);
                sp.setDescribe(price);
                sp.setInventory(quantity);

                sp.setAvatar(linkEdit);

                boolean check = movieDao.updateSanPham(sp);
                if (check) {
                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = movieDao.getListSanPham();
                    notifyDataSetChanged();
                    //đóng dialog
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Bắt sự kiện cho btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //show dialog khi gọi
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNameMovie, tvCategoryandtime,tvPremieretime;
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
