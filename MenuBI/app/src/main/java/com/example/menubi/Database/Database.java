package com.example.menubi.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.menubi.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="editbd.db";
    private static final int DB_VER=1;
    public Database(Context context) {

        super(context, DB_NAME, null, DB_VER);
    }
    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder  qb= new SQLiteQueryBuilder();
        String[] sqlSelect = {"ProductName","ProductId","Quantite","Prix","Discount"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()){
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantite")),
                        c.getString(c.getColumnIndex("Prix")),
                       c.getString(c.getColumnIndex("Discount"))));
            }while (c.moveToNext());
        }
        return result;
    }
    public void addToCart(Order order){
        SQLiteDatabase db  = getReadableDatabase();
        String querry = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantite,Prix,Discount) VALUES ('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantite(),
                order.getPrix(),
                order.getDiscount()
                );
        db.execSQL(querry);
    }
    public void cleanCart(){
        SQLiteDatabase db  = getReadableDatabase();
        String querry = String.format("DELETE FROM OrderDetail");

        db.execSQL(querry);
    }
    //favorite food
    public void addFavorite(String foodId){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("INSERT INTO Favorite(FoodId) VALUES ('%s');",foodId);
        db.execSQL(query);

    }
    public void removeFromFavorite(String foodId){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("DELETE FROM Favorite WHERE FoodId='%s';",foodId);
        db.execSQL(query);

    }

    public boolean isFavorite(String foodId){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorite WHERE FoodId='%s';",foodId);
        Cursor c = db.rawQuery(query,null);
        if (c.getCount() <= 0 ){
            c.close();
            return false;
        }
        c.close();
        return true;


    }

}
