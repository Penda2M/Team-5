package com.example.menubi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.menubi.Database.Database;
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
    Database dbLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_plat);
        database=FirebaseDatabase.getInstance();
        dbLocal = new Database(this);
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
            protected void populateViewHolder(final FoodViewHolder foodViewHolder, final Plat plat, final int i) {
                foodViewHolder.food_name.setText(plat.getNomPlat());
                Picasso.with(getBaseContext()).load(plat.getImage()).into(foodViewHolder.food_image);
                //add favorite
               /* if (dbLocal.isFavorite(adapter.getRef(i).getKey()))
                    foodViewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                foodViewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!dbLocal.isFavorite(adapter.getRef(i).getKey()))
                        {
                            dbLocal.addFavorite(adapter.getRef(i).getKey());
                            foodViewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(ListePlat.this,""+plat.getNomPlat()+"added to favorite",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dbLocal.removeFromFavorite( adapter.getRef(i).getKey());
                            foodViewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(ListePlat.this,""+plat.getNomPlat()+"removed From favorite",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
                final Plat local = plat;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongClick) {
                        Intent detailPlat = new Intent(ListePlat.this,DetailsPlats.class);
                        detailPlat.putExtra("PlatId",adapter.getRef(position).getKey());
                        startActivity(detailPlat);
                    }
                });

            }
          };
        recyclerView.setAdapter(adapter);
    }
}
