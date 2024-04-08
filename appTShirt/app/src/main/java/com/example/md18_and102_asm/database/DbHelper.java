package com.example.md18_and102_asm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.md18_and102_asm.model.Cart;
import com.example.md18_and102_asm.model.Note;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "QLP", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qND = "create table Account(username text primary key, password text, name text)";
        db.execSQL(qND);
          String dataAC ="insert into Account(username,password,name)values" +
                  "('QueDan','QueDan2006@','Lê hữu luân')";
          db.execSQL(dataAC);
//        FOREIGN KEY (username) REFERENCES Account(username)
 // bảng giỏ hàng
        String qc = "create table Cart(namecart text primary key,sizecart text,quantitycart text, pricecart text ,username text, FOREIGN KEY (username) REFERENCES Account(username)  )";
        db.execSQL(qc);
          String Cart ="insert into Cart(namecart,sizecart,quantitycart,pricecart,username)values"+
                  "('Áo thun tay lỡ ','33','2','200000','QueDan')";
          db.execSQL(Cart);


        String qSP = "create table Shirt(idmv integer primary key autoincrement," +
                " nameshirt text,describe text, inventory text, avatar text)";
        db.execSQL(qSP);
        String data = "insert into Shirt(nameshirt,describe,inventory,avatar) values" +
                "('Áo thun tay lỡ','Áo này rất đẹp','300','anh1')," +
                "('Áo thun đen','Áo này siêu đẹp','200','anh2')," +
                "('Áo thun One Pice','Áo này đẹp','100','anh3')," +
                "('Áo dành cho nữ','Áo nữ này đep','1123','anh4')," +
                "('Áo dành cho nữ','Free Size','199','anh1')";
        db.execSQL(data);




        String qN ="create table Note(namemovietry text,time text,idchair integer,quantity integer,price integer)";
        db.execSQL(qN);
        String dataN ="insert into Note(namemovietry,time,idchair,quantity,price) values" +
                "('Áo dành cho nữ','S','Thanh toán giỏ hàng',1,100000)," +
                "('Áo thun One Pice: Red','XL','Đã thanh toán',1,100000)," +
                "('Áo thun đen','XXL','Thanh toán giỏ hàng',1,100000)";

        db.execSQL(dataN);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Account");
        db.execSQL("drop table if exists Shirt");
        db.execSQL("drop table if exists Note");
        db.execSQL("drop table if exists Cart");

    }

    //Hàm checkUsername
    public boolean checkcart(String namecart){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from Cart where namecart=?",
                new String[]{namecart});
        if (c.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }



    // updata note
    public void updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("namemovietry", note.getNamemovietry());
        values.put("time", note.getTime());
        values.put("idchair", note.getIdchair());
        values.put("quantity", note.getQuantity());
        values.put("price", note.getPrice());
        db.insert("Note",null,values);

        db.close();
    }

    // updata note
    public void updateCart(Cart cart) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("namecart", cart.getNamecart());
        values.put("sizecart", cart.getSizecart());
        values.put("quantitycart", cart.getQuantitycart());
        values.put("pricecart", cart.getPricecart());
        values.put("username",cart.getUsername());
        db.insert("Cart",null,values);

        db.close();
    }

    //Hàm register
    public void register(String username, String password, String name){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("password",password);
        cv.put("name",name);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("Account",null,cv);
        db.close();
    }

    //Hàm login
    public int login(String username, String password){
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from Account where username=? and password=?",str);
        if (c.moveToNext()){
            result = 1;
        }
        return result;
    }
    //Hàm checkUsername
    public boolean checkUsername(String username){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from Account where username=?",
                new String[]{username});
        if (c.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }
    //Hàm updatePassword
    public boolean updatePassword(String username, String password){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long result = sqLiteDatabase.update("Account",contentValues,"username=?",
                new String[]{username});
        return result != -1;
    }



}
