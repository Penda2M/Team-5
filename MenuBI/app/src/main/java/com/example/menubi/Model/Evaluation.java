package com.example.menubi.Model;

public class Evaluation {
    private  String userRef;
    private  String foodId;
    private  String rateValue;
    private  String comment;

    public Evaluation() {
    }

    public Evaluation(String userRef, String foodId, String rateValue, String comment) {
        this.userRef = userRef;
        this.foodId = foodId;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
