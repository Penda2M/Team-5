package com.example.menubi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button btnSignup, btnSignin;
    TextView txtSlogan;
    private MainActivity myAct;

   /* Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    });*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        txtSlogan= (TextView) findViewById(R.id.txtSlogan);
        btnSignin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             /*   AlertDialog.Builder mypopup= new AlertDialog.Builder(myAct);
                mypopup.setTitle("Alerte");
                mypopup.setMessage("Continuer le procéssus?");
                mypopup.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "vous avez cliqué sur OUI", Toast.LENGTH_SHORT).show();
                    }
                });

                mypopup.setPositiveButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "vous avez cliqué sur NON", Toast.LENGTH_SHORT).show();
                    }
                });

             mypopup.show();*/

             final CustomPopup popup=new CustomPopup(myAct);
             popup.setTitre("Alerte");
             popup.setSous_titre("Voulez vous continuer?");
             popup.getBtn_oui().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(getApplicationContext(), "OUI", Toast.LENGTH_SHORT).show();
                     popup.dismiss();
                 }
             });
             popup.getBtn_non().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     popup.dismiss();
                 }
             });
             popup.build();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this,Signup.class);
                startActivity(signup);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(MainActivity.this,Signin.class);
                startActivity(signin);
            }
        });
    }
}
