package com.example.menubi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.menubi.modele.Plat;

public class AjoutePlat extends AppCompatActivity {
    EditText nomPlat,descriptionPlat,prix;
    ImageButton btnInputImage;
    ImageView imageplat;
    Button btnValide;

    Plat plat;


    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoute_plat);

        // initialisation des bouton et editext
        nomPlat = (EditText) findViewById(R.id.nomPlat);
        descriptionPlat = (EditText) findViewById(R.id.descriptionPlat);
        prix = (EditText) findViewById(R.id.prix);
        btnInputImage =(ImageButton) findViewById(R.id.btnInputImage);
        imageplat = (ImageView) findViewById(R.id.imageplat);
        btnValide = (Button) findViewById(R.id.btnValider);

        //Listener Bouton input image
        btnInputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){

                        //permission nom accorder

                        String permissions =  (Manifest.permission.READ_EXTERNAL_STORAGE);
                        requestPermissions(new String[]{permissions}, PERMISSION_CODE);
                    }
                    else {
                        //permission accorder
                        choisirImageGallerie();
                    }

                }
                else {
                    // System os is less then marshmailcw
                    choisirImageGallerie();
                }
            }
        });

        btnValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = nomPlat.getText().toString();
                String desc = descriptionPlat.getText().toString();
                int pr = Integer.parseInt(prix.getText().toString());

                plat.setNomPlat(nom);
                plat.setDescription(desc);
                plat.setPrix(pr);

            }
        });
    }

    private void choisirImageGallerie() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //resultat de  image choisi


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE ){
            imageplat.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission est accorder
                }
                else {
                    //permission n'est pas accordé
                    Toast.makeText(this,"Permission non accordé", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
