package com.example.md18_and102_asm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.md18_and102_asm.Account.Cartacti;
import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.adapter.CartAdapter;
import com.example.md18_and102_asm.dao.ShirtDao;
import com.example.md18_and102_asm.database.DbHelper;
import com.example.md18_and102_asm.model.Cart;
import com.example.md18_and102_asm.model.Note;

import java.util.ArrayList;


public class GetChairActivity extends AppCompatActivity implements View.OnClickListener {
    ViewGroup layout;
    private DbHelper dbHelper;
    Button btnok,tbnnext;

    TextView tvdata1, tvdata2,tvdata3,tvdata4,tvdata5;

ArrayList<Cart> listcart;

ShirtDao shirtDao;

CartAdapter cartAdapter;
    private static final String PREF_KEY = "products_data"; // Shared preference key


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_chair);

        dbHelper = new DbHelper(GetChairActivity.this); // Pass the context


        btnok = findViewById(R.id.btnok);
        tbnnext = findViewById(R.id.tbnnext);
        tvdata1 = findViewById(R.id.tvdata1);
        tvdata2 = findViewById(R.id.tvdata2);
        tvdata3 = findViewById(R.id.tvdata3);
       // tvdata4 = findViewById(R.id.tvdata4);
        tvdata5 = findViewById(R.id.tvdata5);


        // đổ dữ liệu của cái tên , số lượng , và giờ chiếu phim lên text view;
        String[] selectedData = getIntent().getStringArrayExtra("selectedData");
        tvdata1.setText(selectedData[0]); // tên
        tvdata2.setText(selectedData[1]); //giờ
        tvdata3.setText(selectedData[3]);  //số lượng
        tvdata5.setText(selectedData[4]);  // giá


                        // Trích xuất các giá trị từ dữ liệu đã chọn
                        String namemovietry = tvdata1.getText().toString();
                        String time = tvdata2.getText().toString();
                        String quaitlyStr = tvdata3.getText().toString(); // Lưu ID dưới dạng chuỗi cho đến lúc này
                        String price = tvdata5.getText().toString();






                 tbnnext.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         // truyền vào trong dbhelper
                         Note note = new Note();
                         note.setNamemovietry(namemovietry);
                         note.setTime(time);
                         note.setQuantity(Integer.parseInt(quaitlyStr));
                         note.setIdchair("đã thanh toán");
                         note.setPrice(Integer.parseInt(price));
                         // Cập nhật ghi chú trong cơ sở dữ liệu
                         DbHelper dbHelper = new DbHelper(GetChairActivity.this);
                         dbHelper.updateNote(note);

                         Toast.makeText(GetChairActivity.this, "thanh toan thanh cong", Toast.LENGTH_SHORT).show();

//
//                         FragmentManager fragmentManager = getSupportFragmentManager();
//                         FragmentTransaction transaction = fragmentManager.beginTransaction();
//                         NoteFragment noteFragment = new NoteFragment();
//                         transaction.add(R.id.fragmentContainer, noteFragment);
//                         transaction.commit();
                     }
                 });


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                boolean check = dbHelper.checkcart(namemovietry);
                if (check) {
                    Toast.makeText(GetChairActivity.this, "Sản phẩm đã Tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(GetChairActivity.this, Cartacti.class);
//                    startActivity(intent);

                }
                else {
                    Cart cart = new Cart();

                    cart.setNamecart(namemovietry);
                    cart.setSizecart(time);
                    cart.setQuantitycart(quaitlyStr);
                    cart.setPricecart(price);
                    cart.setUsername("luân");

                    // Cập nhật ghi chú trong cơ sở dữ liệu
                    DbHelper dbHelper = new DbHelper(GetChairActivity.this);
                    dbHelper.updateCart(cart);
                    Toast.makeText(GetChairActivity.this, " đã thêm vào gior hàng", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(GetChairActivity.this, Cartacti.class);
                    startActivity(intent);

                }
            }





});



}

    @Override
    public void onClick(View v) {

    }
}