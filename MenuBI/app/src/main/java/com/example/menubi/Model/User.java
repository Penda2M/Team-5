package com.example.menubi.Model;

public class User {
    private String Password;
    private String Profil;

    public User() {
    }

    public User(String password, String profil) {
        Password = password;
        Profil = profil;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getProfil() {
        return Profil;
    }

    public void setProfil(String profil) {
        Profil = profil;
    }
}
