package com.example.menubi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.menubi.Model.Plat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AjoutePlat extends AppCompatActivity {
    EditText nomPlat,descriptionPlat,prix;
    ImageButton btnInputImage;
    ImageView imageplat;
    Button btnValide;
    Spinner categorie;
    Plat plat;
    DatabaseReference reff;
    StorageReference Folder;
    String imageUrl;
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;

    String categ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoute_plat);

        // initialisation des bouton et editext
        nomPlat = (EditText) findViewById(R.id.nomPlat);
        descriptionPlat = (EditText) findViewById(R.id.descriptionPlat);
        prix = (EditText) findViewById(R.id.prix);
        btnInputImage =(ImageButton) findViewById(R.id.btnInputImage);
        categorie = (Spinner) findViewById(R.id.categorie);
        imageplat = (ImageView) findViewById(R.id.imageplat);
        btnValide = (Button) findViewById(R.id.btnValider);
        plat= new Plat();


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorie, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        categorie.setAdapter(adapter);

        switch (categorie.getSelectedItem().toString()){
            case "Entrées": categ="01";
            case "desserts": categ= "02";
            case "Plats de resistance": categ = "03";
            case "Boisson": categ = "04";
        }

        reff = FirebaseDatabase.getInstance().getReference().child("Plat");
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
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
                String pr = prix.getText().toString();

                plat.setNomPlat(nom);
                plat.setDescription(desc);
                plat.setPrix(pr);
                plat.setMenuId(categ);
                plat.setImage(imageUrl);
                plat.setDiscount("10");
                reff.push().setValue(plat);
                System.out.println(imageUrl);
                Toast.makeText(AjoutePlat.this,"Donnée inserer avec succes", Toast.LENGTH_LONG).show();

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
            /*
            final Uri ImageData = data.getData();

            final StorageReference imagename = Folder.child("image"+ImageData.getLastPathSegment());
            imagename.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = String.valueOf(uri);
                        }
                    });
                }
            })*/
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
