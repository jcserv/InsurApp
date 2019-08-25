package com.example.android.microinsurance.home.model;

import com.google.gson.annotations.SerializedName;

public class RequestResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String title;

    @SerializedName("confidence")
    private float confidence;

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    @SerializedName("value")
    private int value;

    @SerializedName("purchaseDate")
    private String purchaseDate;

    public RequestResponse(String id,String title, int confidence, String category, String description, int value, String purchaseDate) {
        this.id = id;
        this.title = title;
        this.confidence = confidence;
        this.category = category;
        this.description = description;
        this.value = value;
        this.purchaseDate = purchaseDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getConfidence() {
        return confidence;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}