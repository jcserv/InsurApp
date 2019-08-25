package com.example.android.microinsurance.camera.model;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    @SerializedName("name")
    private String title;

    @SerializedName("confidence")
    private int confidence;

    @SerializedName("description")
    private String description;

    @SerializedName("value")
    private int value;

    @SerializedName("purchaseDate")
    private String purchaseDate;

    public ImageResponse(String title, int confidence, String description, int value, String purchaseDate) {
        this.title = title;
        this.confidence = confidence;
        this.description = description;
        this.value = value;
        this.purchaseDate = purchaseDate;
    }

    public String getTitle() {
        return title;
    }

    public int getConfidence() {
        return confidence;
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