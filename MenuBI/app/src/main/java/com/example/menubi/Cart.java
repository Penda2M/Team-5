package com.example.menubi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menubi.Common.Common;
import com.example.menubi.Database.Database;
import com.example.menubi.Model.Order;
import com.example.menubi.Model.Request;
import com.example.menubi.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotal;
    FButton btnPlace;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    String total, refTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database =FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        //init
        recyclerView=(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotal = (TextView)findViewById(R.id.total);
        btnPlace = (FButton) findViewById(R.id.btnPlaceOrder);
        loadListFood();
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request= new Request(
                        Common.curentUser.getRefTable(),
                        Common.curentUser.getProfil(),
                        txtTotal.getText().toString(),
                        cart

                );
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Merci pour la commande",Toast.LENGTH_SHORT).show();

               // invocation de la classe clientPay  finish();
                Intent intCart = new Intent(Cart.this,ClientPay.class);
                intCart.putExtra("total",txtTotal.getText().toString());
                intCart.putExtra("refTable",Common.curentUser.getRefTable());
                startActivity(intCart);
            }
        });
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter= new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);
        // calcule totale
        int total =0;
        for (Order order:cart){
            total+= (Integer.parseInt(order.getPrix()))*(Integer.parseInt(order.getQuantite()));
            Locale locale = new Locale("fr","FR");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            txtTotal.setText(fmt.format(total));
        }
    }
}
