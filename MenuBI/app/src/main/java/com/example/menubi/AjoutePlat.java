package com.example.menubi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.menubi.Model.Plat;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class AjoutePlat extends AppCompatActivity {
    EditText nomPlat, descriptionPlat, prix;
    ImageButton btnInputImage;
    ImageView imageplat;
    Button btnValide;
    Spinner categorie;
    Plat plat;

    DatabaseReference reff;
    StorageReference refStorage;
    String imageUrl,nom,desc,pr;
    Uri uriImage;

    StorageTask uploadTask;


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
        btnInputImage = (ImageButton) findViewById(R.id.btnInputImage);
        categorie = (Spinner) findViewById(R.id.categorie);
        imageplat = (ImageView) findViewById(R.id.imageplat);
        btnValide = (Button) findViewById(R.id.btnValider);
       // plat = new Plat();


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorie, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        categorie.setAdapter(adapter);




        refStorage = FirebaseStorage.getInstance().getReference("plats");
        reff = FirebaseDatabase.getInstance().getReference().child("Plat");
        // refStorage= FirebaseStorage.getInstance().getReference().child("ImageFolder");
        //Listener Bouton input image
        btnInputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {

                        //permission nom accorder

                        String permissions = (Manifest.permission.READ_EXTERNAL_STORAGE);
                        requestPermissions(new String[]{permissions}, PERMISSION_CODE);
                    } else {
                        //permission accorder
                        choisirImageGallerie();
                    }

                } else {
                    // System os is less then marshmailcw
                    choisirImageGallerie();
                }
            }
        });

        btnValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(AjoutePlat.this);
                pd.setMessage("Patienter svp...");
                pd.show();

                switch (categorie.getSelectedItem().toString()) {
                    case "Entrées":
                        categ = "01";break;
                    case "desserts":
                        categ = "02";break;
                    case "Plats de resistance":
                        categ = "03";break;
                    case "Boisson":
                        categ = "04";break;
                }

                nom = nomPlat.getText().toString();
                desc = descriptionPlat.getText().toString();
                pr = prix.getText().toString();

                //***********************
                final StorageReference fileReference = refStorage.child(System.currentTimeMillis() +
                        "." + getFileExtension(uriImage));

                uploadTask = fileReference.putFile(uriImage);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isComplete()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                       if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            imageUrl = downloadUri.toString();
                         /*
                            plat.setNomPlat(nom);
                            plat.setDescription(desc);
                            plat.setPrix(pr);
                            plat.setMenuId(categ);
                            plat.setImage(imageUrl);
                            plat.setDiscount("10");*/

                           plat = new Plat(nom,desc,pr,"10",imageUrl,categ);
                           // reff.push().setValue(plat);
                            reff.child(""+(int)(20+ new Random().nextInt(250-10))).setValue(plat);
                            pd.dismiss();
                            Toast.makeText(AjoutePlat.this, "Donnée inserer avec succes", Toast.LENGTH_LONG).show();

                            prix.getText().clear();
                            descriptionPlat.getText().clear();
                            nomPlat.getText().clear();
                            imageplat.setImageResource(0);

                        }
                       else {
                           Toast.makeText(AjoutePlat.this, "Erreur", Toast.LENGTH_SHORT).show();
                       }


                    }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AjoutePlat.this, " "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
        private void choisirImageGallerie () {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        }

        //resultat de  image choisi


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

                imageplat.setImageURI(data.getData());
                uriImage = data.getData();
            //    Toast.makeText(AjoutePlat.this,""+uriImage.toString(),Toast.LENGTH_LONG).show();
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
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch (requestCode) {
                case PERMISSION_CODE: {
                    if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //permission est accorder
                    } else {
                        //permission n'est pas accordé
                        Toast.makeText(this, "Permission non accordé", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        private String getFileExtension (Uri uri){
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }

}