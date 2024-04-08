package com.example.md18_and102_asm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.database.DbHelper;
import com.example.md18_and102_asm.model.Cart;
import com.example.md18_and102_asm.model.Note;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {
    private final Context context;
    private ArrayList<Cart> listc;
   public int tong = 0;
    private final ShirtDao cartdao;
    DbHelper dbHelper;
    private TotalValueListener totalValueListener;


    public CartAdapter(Context context, ArrayList<Cart> listc, ShirtDao cartdao,TotalValueListener listener) {
        this.context = context;
        this.listc = listc;
        this.cartdao = cartdao;
        this.totalValueListener = listener;

    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_ryccart, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
//set dữ liệu lên từng item trên recycler
        holder.namecart.setText(listc.get(position).getNamecart());
        holder.sizecart.setText(listc.get(position).getSizecart());
        holder.quaicart.setText(listc.get(position).getQuantitycart());
        holder.pricecart.setText(listc.get(position).getPricecart());

        holder.seleccart.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Thêm giá trị vào tổng giá trị nếu được chọn
                int currentPrice = Integer.parseInt(listc.get(position).getPricecart());
                 tong = tong + currentPrice;
                 passTotalValue(tong);

                // truyền vào trong dbhelper
                Note note = new Note();
                note.setNamemovietry( listc.get(position).getNamecart());
                note.setTime(listc.get(position).getSizecart());
                note.setQuantity(Integer.parseInt(listc.get(position).getQuantitycart()));
                note.setIdchair("thanh toán giỏ hàng");
                note.setPrice(Integer.parseInt(listc.get(position).getPricecart()));
                // Cập nhật ghi chú trong cơ sở dữ liệu
                DbHelper dbHelper = new DbHelper(context);
                dbHelper.updateNote(note);



            } else {
                // Xóa giá trị khỏi tổng giá trị nếu không được chọn
                int currentPrice = Integer.parseInt(listc.get(position).getPricecart());
                tong = tong - currentPrice;
                passTotalValue(tong);

//                String namecart = listc.get(holder.getAdapterPosition()).getNamecart();
//                boolean check = cartdao.deleteCart(namecart);
//                if (check) {
//                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
//                    listc.clear();
//                    listc = cartdao.getListgiohang();
//                    notifyItemRemoved(holder.getAdapterPosition());
//                } else {
//                    Toast.makeText(context, "Failed Delete", Toast.LENGTH_SHORT).show();
//                }

            }
        });


    }



    @Override
    public int getItemCount() {
        return listc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namecart,sizecart,quaicart,pricecart;
        public CheckBox seleccart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namecart = itemView.findViewById(R.id.namecart);
            sizecart = itemView.findViewById(R.id.sizecart);
            quaicart = itemView.findViewById(R.id.quaicart);
            pricecart = itemView.findViewById(R.id.pricecart);
            seleccart = itemView.findViewById(R.id.seleccart);

        }
    }

//    public void passTotalValue(int total) {
////        Intent intent = new Intent(context, Cartacti.class);
////        intent.putExtra("total", total);
////        context.startActivity(intent);
//

//        }
//    }

    public void passTotalValue(int total) {
        if (totalValueListener != null) {
            totalValueListener.onTotalValueChanged(total);
        }
    }

    public interface TotalValueListener {
        void onTotalValueChanged(int total);
    }


}


