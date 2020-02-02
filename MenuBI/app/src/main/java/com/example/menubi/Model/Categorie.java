package com.example.menubi.Model;

public class Categorie {
    private String Name;
    private String Image;

    public Categorie() {
    }

    public Categorie(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
