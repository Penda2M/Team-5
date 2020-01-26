package com.example.menubi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.menubi.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Signup extends AppCompatActivity {
        MaterialEditText edtPhone,edtRef,edtMdp;
        Button btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtPhone=(MaterialEditText) findViewById(R.id.edtPhone);
        edtRef=(MaterialEditText) findViewById(R.id.edtRef);
        edtMdp=(MaterialEditText) findViewById(R.id.edtMdp);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = db.getReference("User");
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(Signup.this);
                pd.setMessage("Patienter svp...");
                pd.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            pd.dismiss();
                            Toast.makeText(Signup.this, "Numero deja enregistrer", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            pd.dismiss();
                            User user = new User(edtRef.getText().toString(),edtMdp.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(Signup.this, "Inscription reussit", Toast.LENGTH_SHORT).show();
                            finish();
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
