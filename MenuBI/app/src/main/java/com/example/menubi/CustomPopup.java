package com.example.menubi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomPopup extends Dialog {

    private String titre;
    private String sous_titre;
    private Button btn_oui, btn_non;
    private TextView txt_popup, txt_sous_titre ;

    public CustomPopup(Activity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.my_popup_template);

        this.titre="Mon titre";
        this.sous_titre="Mon sous titre";
        this.btn_oui=findViewById(R.id.btn_oui);
        this.btn_non=findViewById(R.id.btn_non);
        this.txt_popup=findViewById(R.id.txt_popup);
        this.txt_sous_titre=findViewById(R.id.txt_sous_titre);


    }

    public void setTitre(String titre){
        this.titre="Titre";
    }

    public void setSous_titre(String sous_titre){
        this.sous_titre="Sous titre";
    }

    public Button getBtn_oui(){
        return btn_oui;}

    public Button getBtn_non() {
        return btn_non;
    }

    public void build(){
        show();
    txt_popup.setText(titre);
    txt_sous_titre.setText(sous_titre);
    }
}
