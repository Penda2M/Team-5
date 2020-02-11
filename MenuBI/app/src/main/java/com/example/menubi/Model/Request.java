package com.example.menubi.Model;

import java.util.List;

public class Request {
    private String RefTable;
    private String profil;
    private String total;
    private String status;
    private List<Order> foods;

    public Request() {
    }

    public Request(String refTable, String profil, String total, List<Order> foods) {
        RefTable = refTable;
        this.profil = profil;
        this.total = total;
        this.foods = foods;
        this.status = "0";
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefTable() {
        return RefTable;
    }

    public void setRefTable(String refTable) {
        RefTable = refTable;
    }

    public String getNomPlat() {
        return profil;
    }

    public void setNomPlat(String nomPlat) {
        this.profil = nomPlat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
