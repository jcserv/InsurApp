package com.example.android.microinsurance.camera.model;

import com.google.gson.annotations.SerializedName;

public class ImageRequest {
    @SerializedName("name")
    private String title;

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    @SerializedName("value")
    private int value;

    @SerializedName("purchaseDate")
    private String purchaseDate;

    @SerializedName("binary")
    private String encode;

    public ImageRequest(String title, String category, String description, int value, String purchaseDate, String encode) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.value = value;
        this.purchaseDate = purchaseDate;
        this.encode = encode;
    }

    public String getTitle() {
        return title;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getEncode() {
        return encode;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
