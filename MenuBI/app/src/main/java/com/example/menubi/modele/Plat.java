package com.example.menubi.modele;

import android.media.Image;

public class Plat {
    private String nomPlat;
    private String description;
    private int prix;


    public Plat(String nomPlat, String description, int prix) {
        this.nomPlat = nomPlat;
        this.description = description;
        this.prix = prix;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
