package com.example.menubi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.menubi.Database.Database;
import com.example.menubi.Model.Order;
import com.example.menubi.Model.Plat;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailsPlats extends AppCompatActivity {
    TextView food_name, food_price, food_description;
    ImageView image_food;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference plats;
    Plat plat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_plats);
        //db
        database=FirebaseDatabase.getInstance();
        plats = database.getReference("Plat");
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btn_cart);
       btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart( new Order(
                        foodId,
                        plat.getNomPlat(),
                        numberButton.getNumber(),
                        plat.getPrix(),
                        plat.getDiscount()
                ));
                Toast.makeText(DetailsPlats.this,"added to Cart",Toast.LENGTH_SHORT).show();
            }
        });
        food_name= (TextView)findViewById(R.id.food_name);
        food_description= (TextView)findViewById(R.id.food_description);
        food_price= (TextView)findViewById(R.id.food_price);
        image_food= (ImageView)findViewById(R.id.img_plat);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpendedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        //recuperation platId
        if (getIntent() !=null)
            foodId = getIntent().getStringExtra("PlatId");
        if (!foodId.isEmpty()){
            getDetailPlat(foodId);
        }

    }

    private void getDetailPlat(String foodId) {
        plats.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plat = dataSnapshot.getValue(Plat.class);
                //set image
                Picasso.with(getBaseContext()).load(plat.getImage()).into(image_food);
                collapsingToolbarLayout.setTitle(plat.getNomPlat()  );
                food_price.setText(plat.getPrix());
                food_name.setText(plat.getNomPlat());
                food_description.setText(plat.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
