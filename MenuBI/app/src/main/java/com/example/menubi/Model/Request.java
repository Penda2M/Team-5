package com.example.menubi.Model;

import java.util.List;

public class Request {
    private String RefTable;
    private String profil;
    private String total;
    private List<Order> foods;

    public Request() {
    }

    public Request(String refTable, String profil, String total, List<Order> foods) {
        RefTable = refTable;
        this.profil = profil;
        this.total = total;
        this.foods = foods;
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
