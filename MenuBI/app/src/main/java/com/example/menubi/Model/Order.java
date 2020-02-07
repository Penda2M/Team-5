package com.example.menubi.Model;

public class Order {
    private String ProductId;
    private String ProductName;
    private String Quantite;
    private String Prix;
    private String Discount;

    public Order() {
    }

    public Order(String productId, String productName, String quantite, String prix, String discount) {
        ProductId = productId;
        ProductName = productName;
        Quantite = quantite;
        Prix = prix;
        Discount = discount;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantite() {
        return Quantite;
    }

    public void setQuantite(String quantite) {
        Quantite = quantite;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
