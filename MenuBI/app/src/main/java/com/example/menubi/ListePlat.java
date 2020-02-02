package com.example.menubi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.menubi.Interface.ItemClickListener;
import com.example.menubi.Model.Plat;
import com.example.menubi.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ListePlat extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference listePlat;
    String categorieId="";
    FirebaseRecyclerAdapter<Plat, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_plat);
        database=FirebaseDatabase.getInstance();
        listePlat = database.getReference("Plat");
        recyclerView = (RecyclerView) findViewById(R.id.reci_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (getIntent() !=null)
            categorieId=getIntent().getStringExtra("CategorieId");
        if (!categorieId.isEmpty() && categorieId !=null){
            loadListePlat(categorieId);
        }
    }

    private void loadListePlat(String categorieId) {
        adapter= new FirebaseRecyclerAdapter<Plat, FoodViewHolder>(Plat.class,
                R.layout.plat_item,FoodViewHolder.class,
                listePlat.orderByChild("menuId").equalTo(categorieId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Plat plat, int i) {
                foodViewHolder.food_name.setText(plat.getNomPlat());
                Picasso.with(getBaseContext()).load(plat.getImage()).into(foodViewHolder.food_image);
                final Plat local = plat;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongClick) {
                        Toast.makeText(ListePlat.this,""+local.getNomPlat(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
          };
        recyclerView.setAdapter(adapter);
    }
}
