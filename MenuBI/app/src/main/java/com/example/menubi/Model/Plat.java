package com.example.menubi.Model;

import android.media.Image;

public class Plat {
    private String nomPlat;
    private String description;
    private String prix;
    private String image;
    private String menuId;

    public Plat() {
    }

    public Plat(String nomPlat, String description, String prix, String image, String menuId) {
        this.nomPlat = nomPlat;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.menuId = menuId;
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

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
