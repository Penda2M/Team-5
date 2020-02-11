package com.example.menubi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.menubi.Common.Common;
import com.example.menubi.Model.Evaluation;
import com.example.menubi.Model.Plat;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DetailsPlats extends AppCompatActivity implements RatingDialogListener {
    TextView food_name, food_price, food_description;
    ImageView image_food;
    FloatingActionButton btnCart, btnRating;
    ElegantNumberButton numberButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference plats;
    DatabaseReference ratingTbl;
    Plat plat;
    RatingBar ratingBar;
    SQLiteDataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_plats);
        //db
        db = new SQLiteDataBaseHelper(this);
        database=FirebaseDatabase.getInstance();
        plats = database.getReference("Plat");
        ratingTbl = database.getReference("Evaluation");
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btn_cart);
        btnRating = (FloatingActionButton)findViewById(R.id.btn_rating);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar) ;

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

       btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInsert = db.addToCar(foodId,plat.getNomPlat().toString(),numberButton.getNumber().toString()
                ,plat.getPrix().toString(),plat.getDiscount().toString());

               if (isInsert==true){
                   Toast.makeText(DetailsPlats.this," Ajouté au panier",Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(DetailsPlats.this," Non Ajout",Toast.LENGTH_SHORT).show();
               }
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
            getRatingFood(foodId);
        }

    }

    private void getRatingFood(String foodId) {
        Query foodRating = ratingTbl.orderByChild("foodId").equalTo(foodId);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count =0, sum =0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnashot:dataSnapshot.getChildren()){
                    Evaluation item = postSnashot.getValue(Evaluation.class);
                    sum+=Integer.parseInt(item.getRateValue());
                    count++;
                }
                if (count !=0){
                    float average = sum/count;
                    ratingBar.setRating(average);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Très mauvais","Pas Bon","Assez Bien","Très Bon","Excellent"))
                .setDefaultRating(1)
                .setTitle("Noter Cette nouriture")
                .setDescription("Veuillez sélectionner quelques étoiles et donnez votre avis")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Veuillez saisir votre commentaire ici")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(DetailsPlats.this)
                .show();

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

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {
        //recuperer l'evaluation et l'enregistre dans firebase
        final Evaluation eva = new Evaluation(

                Common.curentUser.getRefTable(),
                foodId,
                String.valueOf(value),
                comments
        );

        ratingTbl.child(Common.curentUser.getRefTable()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Common.curentUser.getRefTable()).exists()){
                    ratingTbl.child(Common.curentUser.getRefTable()).removeValue();
                    ratingTbl.child(Common.curentUser.getRefTable()).setValue(eva);
                }else {
                    ratingTbl.child(Common.curentUser.getRefTable()).setValue(eva);
                }
                Toast.makeText(DetailsPlats.this,"Merci pour la notification!!!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
