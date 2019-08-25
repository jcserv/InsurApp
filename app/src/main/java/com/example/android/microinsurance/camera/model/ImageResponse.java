package com.example.android.microinsurance.camera.model;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {

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

    public ImageResponse(String title, int confidence, String category, String description, int value, String purchaseDate) {
        this.title = title;
        this.confidence = confidence;
        this.category = category;
        this.description = description;
        this.value = value;
        this.purchaseDate = purchaseDate;
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