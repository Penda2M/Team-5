package com.example.menubi;

import android.os.Bundle;

import com.example.menubi.Common.Common;
import com.example.menubi.Database.Database;
import com.example.menubi.Model.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClientPay extends AppCompatActivity {

    TextView total, ref;
    String somme, nref;
    Button btPaye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_pay);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //recupération de la somme total et la ref table

        total= (TextView)findViewById(R.id.tables);
        ref=(TextView)findViewById(R.id.refers);
        btPaye=(Button) findViewById(R.id.payer);
        Bundle extra = getIntent().getExtras();
        somme=extra.getString("total");
        nref=extra.getString("refTable");

        total.setText(somme);
        ref.setText(nref);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btPaye.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(ClientPay.this, "Paiement enregistré avec succès", Toast.LENGTH_SHORT).show();
            }});
    }

}
