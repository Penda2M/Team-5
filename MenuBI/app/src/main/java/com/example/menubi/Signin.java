package com.example.menubi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menubi.Common.Common;
import com.example.menubi.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Signin extends AppCompatActivity {
    MaterialEditText edtPhone, edtMdp;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edtPhone=(MaterialEditText) findViewById(R.id.edtPhone);
        edtMdp=(MaterialEditText) findViewById(R.id.edtMdp);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = db.getReference("User");
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(Signin.this);
                pd.setMessage("Patienter svp...");
                pd.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        //Si l'utilisateur n'est pas dans base
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()){
                        pd.dismiss();
                        //imfos sur les utilisateur
                        User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                        if (user.getPassword().equals(edtMdp.getText().toString())){
                            //Toast.makeText(Signin.this, "Vous etes Connecté", Toast.LENGTH_SHORT).show();
                           /* Intent ad = new Intent(Signin.this,Signup.class);
                            Common.curentUser= user;
                            startActivity(ad);
                            finish();*/
                        }
                        else {
                            Toast.makeText(Signin.this, "Connection Echouer", Toast.LENGTH_SHORT).show();
                        }
                        }

                        else {
                            pd.dismiss();
                            Toast.makeText(Signin.this,"L'utilisateur n'existe pas",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}