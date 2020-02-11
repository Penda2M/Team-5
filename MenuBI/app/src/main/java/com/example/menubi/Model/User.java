package com.example.menubi.Model;

public class User {
    private String Password;
    private String Profil;
    private String RefTable;


    public User() {
    }



    public String getPassword() {
        return Password;
    }

    public User(String password, String profil) {
        Password = password;
        Profil = profil;

    }

    public String getRefTable() {
        return RefTable;
    }

    public void setRefTable(String refTable) {
        RefTable = refTable;
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
