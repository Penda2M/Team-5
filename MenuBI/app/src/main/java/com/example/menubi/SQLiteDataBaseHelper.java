package com.example.menubi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.menubi.Model.Order;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
    public  static final String DATABASE_NAME = "editbd.db";
    public  static final String TABLE_CART = "OrderDetail";
    public  static final String TABLE_FAV = "Favorite";
    public  static final String COL_1 = "ID";
    public  static final String COL_2 = "ProductId";
    public  static final String COL_3 = "ProductName";
    public  static final String COL_4 = "Quantite";
    public  static final String COL_5 = "Prix";
    public  static final String COL_6 = "Discount";
    public  static final String COL_7 = "FoodId";
    private static final String CREATE_BDD = "CREATE TABLE "+TABLE_CART+
            " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,ProductId TEXT,ProductName TEXT,Quantite TEXT,Prix TEXT,Discount TEXT) ";

    private static final String CREATE_BDD_FAV = "CREATE TABLE "+TABLE_FAV+
            " (FoodId TEXT PRIMARY KEY ) ";
    public SQLiteDataBaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
        db.execSQL(CREATE_BDD_FAV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FAV);
        onCreate(db);

    }
    public boolean addToCar(String ProductId,String ProductName,String Quantite,String Prix,String Discount){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,ProductId);
        contentValues.put(COL_3,ProductName);
        contentValues.put(COL_4,Quantite);
        contentValues.put(COL_5,Prix);
        contentValues.put(COL_6,Discount);
        long result = db.insert(TABLE_CART,null,contentValues);
        if (result== -1){
            return false;
        }
        else {
            return true;
        }


    }
    public void addFavorit(String FoodId){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_7,FoodId);
        db.insert(TABLE_CART,FoodId,contentValues);

    }
    public boolean isFavorit(String FoodId){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_FAV+" WHERE " +COL_7+ "="+FoodId,null);
        if (c.getCount() <= 0 ){
            c.close();
            return false;
        }
        c.close();
        return true;


    }
   /* public void cleanCart(){
        SQLiteDatabase db  = this.getWritableDatabase();
        String querry = String.format("DELETE FROM OrderDetail");

        db.execSQL(querry);
    }
     public Cursor getAllData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_FAV,null);
        return result;
    }
    */
    //favorite food



    public void removeFromFavorit(String FoodId){
        SQLiteDatabase db  = this.getWritableDatabase();
        String where = FoodId+" = ?";
        String[] whereArgs = {FoodId};

        db.delete(TABLE_FAV, where, whereArgs);

    }





}
