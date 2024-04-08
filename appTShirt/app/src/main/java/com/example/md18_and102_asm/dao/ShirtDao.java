package com.example.md18_and102_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.md18_and102_asm.database.DbHelper;
import com.example.md18_and102_asm.model.Cart;
import com.example.md18_and102_asm.model.Shirt;
import com.example.md18_and102_asm.model.Note;

import java.util.ArrayList;

public class ShirtDao {
    private final DbHelper dbHelper;


    public ShirtDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    //Hàm lấy danh sách sản phẩm
    public ArrayList<Shirt> getListSanPham(){
        //tạo một danh sách để add dữ liệu vào SanPham
        ArrayList<Shirt> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor c = database.rawQuery("select * from Shirt",null);
            if (c.getCount() > 0){
                c.moveToFirst();
                do {
                    list.add(new Shirt(c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4)
                            ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
                c.close();
            }
        }catch (Exception e){
            Log.e("Error", "getListSanPham: " + e);
        }finally {
            database.endTransaction();
        }
        return list;
    }

    public ArrayList<Note> getListSanPham2(){
        //tạo một danh sách để add dữ liệu vào SanPham
        ArrayList<Note> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor c = database.rawQuery("select * from Note",null);
            if (c.getCount() > 0){
                c.moveToFirst();
                do {

                    list.add(new Note(c.getString(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getInt(4)


                    ));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
                c.close();
            }
        }catch (Exception e){
            Log.e("Error", "getListSanPham2: " + e);
        }finally {
            database.endTransaction();
        }
        return list;
    }

// giỏ hàng
public ArrayList<Cart> getListgiohang(){
    //tạo một danh sách để add dữ liệu vào SanPham
    ArrayList<Cart> list = new ArrayList<>();
    SQLiteDatabase database = dbHelper.getReadableDatabase();
    database.beginTransaction();
    try {
        Cursor c = database.rawQuery("select * from Cart",null);
        if (c.getCount() > 0){
            c.moveToFirst();
            do {

                list.add(new Cart(c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4)


                ));
            }while (c.moveToNext());
            database.setTransactionSuccessful();
            c.close();
        }
    }catch (Exception e){
        Log.e("Error", "getListSanPham2: " + e);
    }finally {
        database.endTransaction();
    }
    return list;
}






    //Hàm thêm sản phẩm
    public boolean addSanPham(Shirt movie){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nameshirt",movie.getNameshirt());
        values.put("describe",movie.getDescribe());
        values.put("inventory",movie.getInventory());
        values.put("avatar",movie.getAvatar());

        long check = database.insert("Shirt",null,values);
        return check != -1;
    }

    //Hàm delete sản phẩm
    public boolean deleteSanPham(int idmv){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("shirt","idmv=?",
                new String[]{String.valueOf(idmv)});
        return check != -1;
    }
    public boolean delete(Note note) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("Note", "namemovietry = ?", new String[]{note.getNamemovietry()});

        return check != -1;
    }




    //Hàm delete note
    public boolean deleteCart(String namemovietry){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("Note","namemovietry=?",
                new String[]{String.valueOf(namemovietry)});
        return check != -1;
    }



    //Hàm update sản phẩm
    public boolean updateSanPham(Shirt movieupdate){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nameshirt",movieupdate.getNameshirt());
        values.put("describe",movieupdate.getDescribe());
        values.put("inventory",movieupdate.getInventory());
        values.put("avatar",movieupdate.getAvatar());

        long check = database.update("Shirt",values,"idmv=?",
                new String[]{String.valueOf(movieupdate.getIdmv())});
        return check != -1;
    }
}