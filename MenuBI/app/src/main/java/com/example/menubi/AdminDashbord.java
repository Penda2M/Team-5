package com.example.menubi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashbord extends AppCompatActivity implements View.OnClickListener{
    private CardView btnAjout,btnSupprimer,btnLister,btnModifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashbord);

        //initialisation des BoutonView
        btnAjout = (CardView)findViewById(R.id.btnAjout);
        btnModifier = (CardView) findViewById(R.id.btnModifier);
        btnSupprimer = (CardView) findViewById(R.id.btnSupprimer);
        btnLister =(CardView) findViewById(R.id.btnLister);

        //Ajouter des listener sur les Bouton
        btnAjout.setOnClickListener(this);
        btnSupprimer.setOnClickListener(this);
        btnModifier.setOnClickListener(this);
        btnLister.setOnClickListener(this);
      ;

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.btnAjout : i = new Intent(AdminDashbord.this,AjoutePlat.class); startActivity(i); break;
            case R.id.btnSupprimer : i = new Intent(this,SupprimerPlat.class); startActivity(i); break;
            case R.id.btnModifier : i = new Intent(this,ModifierPlat.class); startActivity(i); break;
            case R.id.btnLister : i = new Intent(this,ListePlat.class); startActivity(i); break;
            default: break;

        }

    }
}
